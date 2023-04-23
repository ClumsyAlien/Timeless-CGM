package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.animation.M24AnimationController;
import com.tac.guns.client.render.animation.MAC10AnimationController;
import com.tac.guns.client.render.animation.module.AnimationMeta;
import com.tac.guns.client.render.animation.module.GunAnimationController;
import com.tac.guns.client.render.animation.module.PlayerHandAnimation;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.render.gun.ModelOverrides;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.item.GunItem;
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
public class micro_uzi_animation implements IOverrideModel {

    //The render method, similar to what is in DartEntity. We can render the item
    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay)
    {
        

        MAC10AnimationController controller = MAC10AnimationController.getInstance();
        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.MICRO_UZI_BODY.getModel(), MAC10AnimationController.INDEX_BODY, transformType, matrices);

            RenderUtil.renderModel(SpecialModels.MICRO_UZI_BODY.getModel(), stack, matrices, renderBuffer, light, overlay);
            RenderUtil.renderModel(SpecialModels.MICRO_UZI_FOLDED_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.MICRO_UZI_BODY.getModel(), MAC10AnimationController.INDEX_MAGAZINE, transformType, matrices);

            if(GunModifierHelper.getAmmoCapacity(stack) > -1)
            {
                RenderUtil.renderModel(SpecialModels.MICRO_UZI_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else
            {
                RenderUtil.renderModel(SpecialModels.MICRO_UZI_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
        }
        matrices.pop();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.MICRO_UZI_BODY.getModel(), MAC10AnimationController.INDEX_BOLT, transformType, matrices);

            Gun gun = ((GunItem) stack.getItem()).getGun();
            float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());
            if(transformType.isFirstPerson()) {
                AnimationMeta reloadEmpty = controller.getAnimationFromLabel(GunAnimationController.AnimationLabel.RELOAD_EMPTY);
                boolean shouldOffset = reloadEmpty != null && reloadEmpty.equals(controller.getPreviousAnimation()) && controller.isAnimationRunning();
                if(!shouldOffset && !Gun.hasAmmo(stack)){
                    matrices.translate(0,0,-0.25);
                }else {
                    matrices.translate(0,0, -0.25 + Math.pow(cooldownOg - 0.5, 2));
                }
            }
            RenderUtil.renderModel(SpecialModels.MICRO_UZI_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        PlayerHandAnimation.render(controller,transformType,matrices,renderBuffer,light);
    }

     

    //TODO comments
}
