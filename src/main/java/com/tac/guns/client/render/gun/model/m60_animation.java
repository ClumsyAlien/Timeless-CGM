package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.init.ModEnchantments;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import com.tac.guns.client.SpecialModels;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Mr. Pineapple
 */
public class m60_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {

        if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.OVER_CAPACITY.get(), entity) > 0)
        {
            RenderUtil.renderModel(SpecialModels.M60_eMAG.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else
        {
            RenderUtil.renderModel(SpecialModels.M60_sMAG.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        RenderUtil.renderModel(SpecialModels.M60.getModel(), stack, matrices, renderBuffer, light, overlay);

        /*
            //Always push
            matrices.pushPose();

            //We're getting the cooldown tracker for the item - items like the sword, ender pearl, and chorus fruit all have this too.
            CooldownTracker tracker = Minecraft.getInstance().player.getCooldowns();
            float cooldownOg = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
             

            matrices.translate(0, 0, 0.238f * cooldown);

            RenderUtil.renderModel(SpecialModels.M1928_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);

            //Always pop
            matrices.popPose();

         */
    }

     

    //TODO comments
}
