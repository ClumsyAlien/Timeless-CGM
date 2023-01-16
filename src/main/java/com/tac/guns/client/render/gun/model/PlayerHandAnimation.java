package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.render.animation.module.GunAnimationController;
import com.tac.guns.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.HumanoidArm;

public class PlayerHandAnimation {
    public static void render(GunAnimationController controller, ItemTransforms.TransformType transformType, PoseStack matrices, MultiBufferSource renderBuffer, int light){
        if(!transformType.firstPerson()) return;
        matrices.pushPose();
        {
            controller.applyRightHandTransform(matrices);
            RenderUtil.renderFirstPersonArm(Minecraft.getInstance().player, HumanoidArm.RIGHT, matrices, renderBuffer, light);
        }
        matrices.popPose();
        matrices.pushPose();
        {
            controller.applyLeftHandTransform(matrices);
            RenderUtil.renderFirstPersonArm(Minecraft.getInstance().player, HumanoidArm.LEFT, matrices, renderBuffer, light);
        }
        matrices.popPose();
    }
}
