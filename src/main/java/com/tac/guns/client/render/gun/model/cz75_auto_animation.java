package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.GunRenderingHandler;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.animation.CZ75AnimationController;
import com.tac.guns.client.render.animation.CZ75AutoAnimationController;
import com.tac.guns.client.render.animation.module.AnimationMeta;
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
import com.tac.guns.util.GunEnchantmentHelper;
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
public class cz75_auto_animation implements IOverrideModel
{
    @Override
    public void render(float partialTicks, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay)
    {
        

        CZ75AutoAnimationController controller = CZ75AutoAnimationController.getInstance();
        GunItem gunItem = ((GunItem) stack.getItem());

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.CZ75_AUTO.getModel(),CZ75AutoAnimationController.INDEX_BODY,transformType,matrices);
            if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.SILENCER.get()) {
                RenderUtil.renderModel(SpecialModels.CZ75_SUPPRESSOR.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_COMPENSATOR.get()) {
                RenderUtil.renderModel(SpecialModels.CZ75_COMPENSATOR.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_BRAKE.get()) {
                RenderUtil.renderModel(SpecialModels.CZ75_BRAKE.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            RenderUtil.renderModel(SpecialModels.CZ75_AUTO.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.CZ75_AUTO.getModel(),CZ75AutoAnimationController.INDEX_MAG,transformType,matrices);
            if (GunModifierHelper.getAmmoCapacityWeight(stack) > -1) {
                RenderUtil.renderModel(SpecialModels.CZ75_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else {
                RenderUtil.renderModel(SpecialModels.CZ75_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
        }
        matrices.pop();

        //Always push
        matrices.push();
        controller.applySpecialModelTransform(SpecialModels.CZ75_AUTO.getModel(),CZ75AutoAnimationController.INDEX_SLIDE,transformType,matrices);
        if(transformType.isFirstPerson()) {
            Gun gun = ((GunItem) stack.getItem()).getGun();
            float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ?
                    1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());

            AnimationMeta reloadEmpty = controller.getAnimationFromLabel(GunAnimationController.AnimationLabel.RELOAD_EMPTY);
            boolean shouldOffset = reloadEmpty != null && reloadEmpty.equals(controller.getPreviousAnimation()) && controller.isAnimationRunning();
            if (Gun.hasAmmo(stack) || shouldOffset) {
                matrices.translate(0, 0, 0.2075f * (-4.5 * Math.pow(cooldownOg - 0.5, 2) + 1.0));
                GunRenderingHandler.get().opticMovement = 0.2075f * (-4.5 * Math.pow(cooldownOg - 0.5, 2) + 1.0);
            } else if (!Gun.hasAmmo(stack)) {
                matrices.translate(0, 0, 0.2075f * (-4.5 * Math.pow(0.5 - 0.5, 2) + 1.0));
                GunRenderingHandler.get().opticMovement = 0.2075f * (-4.5 * Math.pow(0.5 - 0.5, 2) + 1.0);
            }
            matrices.translate(0.00, 0.0, 0.025F);
        }
        RenderUtil.renderModel(SpecialModels.CZ75_AUTO_SLIDE.getModel(), stack, matrices, renderBuffer, light, overlay);

        //Always pop
        matrices.pop();

        PlayerHandAnimation.render(controller,transformType,matrices,renderBuffer,light);
    }
     

    //TODO comments
}
