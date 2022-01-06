package com.tac.guns.client.render.animation;

import com.tac.guns.GunMod;
import com.tac.guns.init.ModItems;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class Glock17AnimationController extends GunAnimationController{
    public static int INDEX_BODY = 5;
    public static int INDEX_LEFT_HAND = 0;
    public static int INDEX_RIGHT_HAND = 7;
    public static int INDEX_MAGAZINE = 4;
    public static int INDEX_SLIDE = 1;
    public static final AnimationMeta RELOAD_NORM = new AnimationMeta(new ResourceLocation("tac","animations/glock_17.gltf"));
    private static final Glock17AnimationController instance = new Glock17AnimationController();

    private Glock17AnimationController(){
        try {
            Animations.load(RELOAD_NORM);
        } catch (IOException e) {
            GunMod.LOGGER.fatal(e.getStackTrace());
        }

        GunAnimationController.setAnimationControllerMap(ModItems.GLOCK_17.getId(),this);
    }

    public static Glock17AnimationController getInstance(){
        return instance;
    }

    @Override
    protected AnimationMeta getAnimationFromLabel(AnimationLabel label) {
        return null;
    }

    @Override
    protected int getAttachmentsNodeIndex() {
        return INDEX_BODY;
    }

    @Override
    protected int getRightHandNodeIndex() {
        return INDEX_BODY;
    }

    @Override
    protected int getLeftHandNodeIndex() {
        return INDEX_LEFT_HAND;
    }
}
