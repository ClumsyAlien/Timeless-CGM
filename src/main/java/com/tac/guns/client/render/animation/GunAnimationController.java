package com.tac.guns.client.render.animation;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public abstract class GunAnimationController {
    private AnimationMeta runningAnimation;
    /*A map to obtain AnimationController through Item, the key value should put the RegistryName of the Item.*/
    private static final Map<ResourceLocation, GunAnimationController> animationControllerMap = new HashMap<>();

    public void runAnimation(AnimationMeta animationMeta){
        Animations.runAnimation(animationMeta);
        runningAnimation = animationMeta;
    }

    public AnimationMeta animationRunning(){
        return runningAnimation;
    }

    public void stopAnimation() {
        if(runningAnimation!=null) {
            Animations.stopAnimation(runningAnimation);
            runningAnimation = null;
        }
    }

    public void runReloadingAnimation(){
        runAnimation(getReloadingAnimation());
    }

    protected abstract AnimationMeta getReloadingAnimation();
    protected abstract int getAttachmentsNodeIndex();
    protected abstract int getRightHandNodeIndex();
    protected abstract int getLeftHandNodeIndex();

    public void applyAttachmentsTransform(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, LivingEntity entity, MatrixStack matrixStack){
        boolean isFirstPerson = transformType.isFirstPerson();
        if( isFirstPerson ) Animations.pushNode(animationRunning(), getAttachmentsNodeIndex());
        Animations.applyAnimationTransform(itemStack, ItemCameraTransforms.TransformType.NONE, entity, matrixStack);
        if( isFirstPerson ) Animations.popNode();
    }

    public void applySpecialModelTransform(IBakedModel model, int index, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack){
        boolean isFirstPerson = transformType.isFirstPerson();
        if( isFirstPerson ) Animations.pushNode(animationRunning(), index);
        Animations.applyAnimationTransform(model, ItemCameraTransforms.TransformType.NONE, matrixStack);
        if( isFirstPerson ) Animations.popNode();
    }

    public void applyRightHandTransform(ItemStack heldItem, LivingEntity entity, MatrixStack matrixStack){
        Animations.pushNode(animationRunning(), getRightHandNodeIndex());
        Animations.applyAnimationTransform(heldItem, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, entity, matrixStack);
        matrixStack.translate(-0.56, 0.52, 0.72);
        Animations.popNode();
    }

    public void applyLeftHandTransform(ItemStack heldItem, LivingEntity entity, MatrixStack matrixStack){
        Animations.pushNode(animationRunning(), getLeftHandNodeIndex());
        Animations.applyAnimationTransform(heldItem, ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, entity, matrixStack);
        matrixStack.translate(-0.56, 0.52, 0.72);
        Animations.popNode();
    }
    public void pushRightHandNode(){
        Animations.pushNode(animationRunning(), getRightHandNodeIndex());
    }
    public void pushLeftHandNode(){
        Animations.pushNode(animationRunning(), getLeftHandNodeIndex());
    }

    /**
     * @param itemRegistryName The RegistryName of the Item.
     * @param animationController The animationController instance you want to register.
     */
    public static void setAnimationControllerMap(ResourceLocation itemRegistryName, GunAnimationController animationController){
        animationControllerMap.put(itemRegistryName, animationController);
    }

    public static GunAnimationController fromItem(Item item){
        return animationControllerMap.get(item.getRegistryName());
    }

    public static GunAnimationController fromRegistryName(ResourceLocation registryName){
        return animationControllerMap.get(registryName);
    }
}
