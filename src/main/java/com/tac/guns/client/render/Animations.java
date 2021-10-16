package com.tac.guns.client.render;

import de.javagl.jgltf.impl.v1.Node;
import de.javagl.jgltf.model.GltfAnimations;
import de.javagl.jgltf.model.NodeModel;
import de.javagl.jgltf.model.animation.Animation;
import de.javagl.jgltf.model.animation.AnimationManager;
import de.javagl.jgltf.model.animation.AnimationRunner;
import de.javagl.jgltf.model.io.GltfAsset;
import de.javagl.jgltf.model.io.GltfAssetReader;
import de.javagl.jgltf.model.io.v2.GltfAssetV2;
import de.javagl.jgltf.model.v2.GltfModelV2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animations {
    public static NodeModel test;
    private static final Map<String, GltfModelV2> gltfModelV2Map = new HashMap<>();
    private static final Map<IBakedModel, NodeModel> nodeModelMap = new HashMap<>();
    private static final Map<String, AnimationRunner> animationRunnerMap = new HashMap<>();
    private static final Map<String, AnimationManager> animationManagerMap = new HashMap<>();

    public static GltfModelV2 load(ResourceLocation resourceLocation) throws IOException {
        IResource resource = Minecraft.getInstance().getResourceManager().getResource(resourceLocation);
        InputStream inputStream = resource.getInputStream();
        GltfAssetReader reader = new GltfAssetReader();
        GltfAsset asset = reader.readWithoutReferences(inputStream);
        if (asset instanceof GltfAssetV2) {
            // put GltfModel into the gltfModelMap
            GltfModelV2 model = new GltfModelV2((GltfAssetV2) asset);
            gltfModelV2Map.put(resourceLocation.toString(), model);
            //refresh animationManagerMap
            List<Animation> animations = GltfAnimations.createModelAnimations(model.getAnimationModels());
            AnimationManager animationManager = new AnimationManager(de.javagl.jgltf.model.animation.AnimationManager.AnimationPolicy.LOOP);
            animationManager.addAnimations(animations);
            animationManagerMap.put(resourceLocation.toString(),animationManager);
            //refresh animationRunnerMap
            stopAnimation(resourceLocation);
            AnimationRunner newRunner = new AnimationRunner(animationManager);
            animationRunnerMap.put(resourceLocation.toString(),newRunner);
            return model;
        }
        inputStream.close();
        return null;
    }

    public static GltfModelV2 getGltfModel(ResourceLocation resourceLocation){
        return gltfModelV2Map.get(resourceLocation.toString());
    }

    public static void bindNode(GltfModelV2 gltfModel, IBakedModel bakedModel, int index){
        nodeModelMap.put(bakedModel, gltfModel.getNodeModels().get(index));
        test = gltfModel.getNodeModels().get(index);
    }

    public static void bindNode(ResourceLocation gltfResource, IBakedModel bakedModel, int index) throws IOException {
        GltfModelV2 gltfModel = getGltfModel(gltfResource);
        if(gltfModel == null) return;
        bindNode(gltfModel,bakedModel,index);
    }

    public static NodeModel getNodeByIBakedModel(IBakedModel bakedModel){
        return nodeModelMap.get(bakedModel);
    }

    public static void removeBind(IBakedModel bakedModel){
        nodeModelMap.remove(bakedModel);
    }

    public static Matrix4f getAnimationTransition(IBakedModel bakedModel){
        NodeModel nodeModel = getNodeByIBakedModel(bakedModel);
        if(nodeModel == null) return null;
        return new Matrix4f(nodeModel.computeGlobalTransform(null));
    }

    public static AnimationRunner getAnimationRunner(ResourceLocation resourceLocation){
        return animationRunnerMap.get(resourceLocation.toString());
    }

    public static void runAnimation(ResourceLocation resourceLocation){
        AnimationRunner runner = getAnimationRunner(resourceLocation);
        if(runner!=null) runner.start();
    }

    public static void stopAnimation(ResourceLocation resourceLocation){
        AnimationRunner runner = getAnimationRunner(resourceLocation);
        if(runner!=null) runner.stop();
    }

    public static boolean isAnimationRunning(ResourceLocation resourceLocation){
        AnimationRunner runner = getAnimationRunner(resourceLocation);
        if(runner == null) return false;
        return runner.isRunning();
    }

    public static AnimationManager getAnimationManager(ResourceLocation resourceLocation){
        return animationManagerMap.get(resourceLocation.toString());
    }
}
