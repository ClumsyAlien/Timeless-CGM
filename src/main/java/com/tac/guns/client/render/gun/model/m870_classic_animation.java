package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.init.ModItems;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Mr. Pineapple
 */
public class m870_classic_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {

        RenderUtil.renderModel(SpecialModels.M870_CLASSIC.getModel(), stack, matrices, renderBuffer, light, overlay);

        //Always push
        matrices.pushPose();

        CooldownTracker tracker = Minecraft.getInstance().player.getCooldowns();
        float cooldownOg = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());

        if (cooldownOg != 0 && cooldownOg < 0.66 || !Gun.hasAmmo(stack)) {
            double cooldownOgTmp = cooldownOg * 1.46;
            if (Gun.hasAmmo(stack)) {
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.translate(0, 0, 0.200f * (-4.5 * Math.pow(cooldownOgTmp - 0.5, 2) + 1.0));
            } else if (!Gun.hasAmmo(stack)) {
                if (cooldownOg > 0.5) {
                    // Math provided by Bomb787 on GitHub and Curseforge!!!
                    matrices.translate(0, 0, 0.200f * (-4.5 * Math.pow(cooldownOgTmp - 0.5, 2) + 1.0));
                }
                else {
                    matrices.translate(0, 0, 0.200f * (-4.5 * Math.pow(0.5 - 0.5, 2) + 1.0));
                }
            }
        }
        matrices.translate(0.00, 0.0, 0.04);
        RenderUtil.renderModel(SpecialModels.M870_CLASSIC_SLIDE.getModel(), stack, matrices, renderBuffer, light, overlay);

        //Always pop
        matrices.popPose();
    }
}
