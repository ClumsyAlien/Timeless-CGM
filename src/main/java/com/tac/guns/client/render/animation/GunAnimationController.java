package com.tac.guns.client.render.animation;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public abstract class GunAnimationController {
    private AnimationMeta runningAnimation;
    /*A map created to obtain AnimationController through items, the key value should put the RegistryName of the Item.*/
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
    public void pushAttachmentsNode(){
        Animations.pushNode(animationRunning(), getAttachmentsNodeIndex());
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
