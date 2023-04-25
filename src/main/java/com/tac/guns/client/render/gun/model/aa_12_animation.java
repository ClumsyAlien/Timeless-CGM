package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.animation.AA12AnimationController;
import com.tac.guns.client.render.animation.module.GunAnimationController;
import com.tac.guns.client.render.animation.module.PlayerHandAnimation;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.render.gun.ModelOverrides;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.init.ModItems;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import com.tac.guns.util.GunModifierHelper;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Timeless Development, and associates.
 */
public class aa_12_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay)
    {
        AA12AnimationController controller = AA12AnimationController.getInstance();
        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.AA_12_BODY.getModel(), AA12AnimationController.INDEX_BODY,transformType,matrices);
            if (Gun.getScope(stack) == null) {
                RenderUtil.renderModel(SpecialModels.AA_12_SIGHT.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.SILENCER.orElse(ItemStack.EMPTY.getItem())) {
                matrices.push();
                matrices.translate(0,0,-.1);
                RenderUtil.renderModel(SpecialModels.AA_12_SILENCER.getModel(), stack, matrices, renderBuffer, light, overlay);
                matrices.translate(0,0,.1);
                matrices.pop();
            }
            else if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_COMPENSATOR.orElse(ItemStack.EMPTY.getItem())) {

                RenderUtil.renderModel(SpecialModels.AA_12_BRAKE.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_BRAKE.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.AA_12_COMPENSATOR.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else {
                RenderUtil.renderModel(SpecialModels.AA_12_MUZZLE.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.SPECIALISED_GRIP.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.AA_12_FRONT_RAIL.getModel(), stack, matrices, renderBuffer, light, overlay);
                RenderUtil.renderModel(SpecialModels.AA_12_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.LIGHT_GRIP.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.AA_12_FRONT_RAIL.getModel(), stack, matrices, renderBuffer, light, overlay);
                RenderUtil.renderModel(SpecialModels.AA_12_LIGHT_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            RenderUtil.renderModel(SpecialModels.AA_12_BODY.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.AA_12_BODY.getModel(), AA12AnimationController.INDEX_MAGAZINE,transformType,matrices);
            if (GunModifierHelper.getAmmoCapacity(stack) > -1) {
                RenderUtil.renderModel(SpecialModels.AA_12_DRUM_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else {
                RenderUtil.renderModel(SpecialModels.AA_12_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
        }
        matrices.pop();

        matrices.push();

        controller.applySpecialModelTransform(SpecialModels.AA_12_BODY.getModel(), AA12AnimationController.INDEX_BOLT,transformType,matrices);
        RenderUtil.renderModel(SpecialModels.AA_12_BOLT_HANDLE.getModel(), stack, matrices, renderBuffer, light, overlay);

        Gun gun = ((GunItem) stack.getItem()).getGun();
        float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());
        if(controller.getAnimationFromLabel(GunAnimationController.AnimationLabel.DRAW).equals(controller.getPreviousAnimation())) cooldownOg = 1;
        if(transformType.isFirstPerson()) {
            if (Gun.hasAmmo(stack)) {
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.translate(0, 0, -0.225f * (-4.5 * Math.pow(cooldownOg - 0.5, 2) + 1.0));
            } else if (!Gun.hasAmmo(stack)) {
                {
                    matrices.translate(0, 0, -0.225f * (-4.5 * Math.pow(0.5 - 0.5, 2) + 1.0));
                }
            }
        }
        RenderUtil.renderModel(SpecialModels.AA_12_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.pop();
        
        PlayerHandAnimation.render(controller,transformType,matrices,renderBuffer,light);
    }
}
