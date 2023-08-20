package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.animation.TEC9AnimationController;
import com.tac.guns.client.render.animation.module.AnimationMeta;
import com.tac.guns.client.render.animation.module.GunAnimationController;
import com.tac.guns.client.render.animation.module.PlayerHandAnimation;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.item.GunItem;
import com.tac.guns.util.GunModifierHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Timeless Development, and associates.
 */
public class tec_9_animation implements IOverrideModel {
    @Override
    public void render(float partialTicks, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {
        TEC9AnimationController controller = TEC9AnimationController.getInstance();

        matrices.push();
        controller.applySpecialModelTransform(SpecialModels.TEC_9_BODY.getModel(), TEC9AnimationController.INDEX_BODY, transformType, matrices);
        RenderUtil.renderModel(SpecialModels.TEC_9_BODY.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.pop();

        matrices.push();
        controller.applySpecialModelTransform(SpecialModels.TEC_9_BODY.getModel(), TEC9AnimationController.INDEX_MAG, transformType, matrices);
        if (GunModifierHelper.getAmmoCapacityWeight(stack) > -1) {
            RenderUtil.renderModel(SpecialModels.TEC_9_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
        } else {
            RenderUtil.renderModel(SpecialModels.TEC_9_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        //Always push
        matrices.push();
        controller.applySpecialModelTransform(SpecialModels.TEC_9_BODY.getModel(), TEC9AnimationController.INDEX_BOLT, transformType, matrices);
        Gun gun = ((GunItem) stack.getItem()).getGun();
        float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());
        if (transformType.isFirstPerson()) {
            AnimationMeta reloadEmpty = controller.getAnimationFromLabel(GunAnimationController.AnimationLabel.RELOAD_EMPTY);
            boolean shouldOffset = reloadEmpty != null && reloadEmpty.equals(controller.getPreviousAnimation()) && controller.isAnimationRunning();
            if (!shouldOffset && !Gun.hasAmmo(stack)) {
                matrices.translate(0, 0, -0.375);
            } else {
                matrices.translate(0, 0, -0.375 + Math.pow(cooldownOg - 0.5, 2));
            }
        }
        RenderUtil.renderModel(SpecialModels.TEC_9_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        //Always pop
        matrices.pop();

        matrices.push();
        controller.applySpecialModelTransform(SpecialModels.TEC_9_BODY.getModel(), TEC9AnimationController.INDEX_BULLET, transformType, matrices);
        RenderUtil.renderModel(SpecialModels.TEC_9_BULLET.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.pop();

        PlayerHandAnimation.render(controller, transformType, matrices, renderBuffer, light);
    }
}
