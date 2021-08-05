package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import com.tac.guns.client.SpecialModels;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Mr. Pineapple
 */
public class ak47_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {

        if(Gun.getScope(stack) != null)
        {
            RenderUtil.renderModel(SpecialModels.AK47_OPTIC_MOUNT.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        RenderUtil.renderModel(SpecialModels.AK47.getModel(), stack, matrices, renderBuffer, light, overlay);

            //Always push
            matrices.pushPose();

            //We're getting the cooldown tracker for the item - items like the sword, ender pearl, and chorus fruit all have this too.
            CooldownTracker tracker = Minecraft.getInstance().player.getCooldowns();
            float cooldownOg = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());

            // Math provided by Bomb787 on GitHub and Curseforge!!!
            matrices.translate(0, 0, 0.190f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1));

            RenderUtil.renderModel(SpecialModels.AK47_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);

            //Always pop
            matrices.popPose();
    }

     

    //TODO comments
}
