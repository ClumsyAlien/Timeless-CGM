package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.GunRenderingHandler;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.animation.CZ75AnimationController;
import com.tac.guns.client.render.animation.FNFALAnimationController;
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
public class fn_fal_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay)
    {
        

        FNFALAnimationController controller = FNFALAnimationController.getInstance();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.FN_FAL.getModel(), FNFALAnimationController.INDEX_BODY,transformType,matrices);
            if(Gun.getScope(stack) != null)
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_MOUNT.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack) == ItemStack.EMPTY && Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack) == ItemStack.EMPTY)
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_STANDARD_HANDGUARD.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_EXTENDED_HANDGUARD.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.ACCELERATOR.get(), stack) > 0)
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_EXTENDED_BARREL.getModel(), stack, matrices, renderBuffer, light, overlay);
                if(GunRenderingHandler.get().muzzleExtraOnEnch == 0 && entity.getHeldItemMainhand() == stack)
                    GunRenderingHandler.get().muzzleExtraOnEnch = -8.125f;
            }
            else
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_STANDARD_BARREL.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.SPECIALISED_GRIP.orElse(ItemStack.EMPTY.getItem()))
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_TAC_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.LIGHT_GRIP.orElse(ItemStack.EMPTY.getItem()))
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_LIGHTWEIGHT_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            RenderUtil.renderModel(SpecialModels.FN_FAL.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        /*if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.LIGHT_GRIP.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.HK_MP5A5_LIGHT_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.SPECIALISED_GRIP.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.HK_MP5A5_TACTICAL_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
        }*/

        /*if(Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack).getItem() == ModItems.STANDARD_FLASHLIGHT.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.AR_15_CQB_STANDARD_FLASHLIGHT.getModel(), stack, matrices, renderBuffer, light, overlay);
        }*/

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.FN_FAL.getModel(), FNFALAnimationController.INDEX_MAGAZINE,transformType,matrices);
            if(GunModifierHelper.getAmmoCapacity(stack) > -1)
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else
            {
                RenderUtil.renderModel(SpecialModels.FN_FAL_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
        }
        matrices.pop();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.FN_FAL.getModel(), FNFALAnimationController.INDEX_HANDLE,transformType,matrices);
            RenderUtil.renderModel(SpecialModels.FN_FAL_HANDLE.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        matrices.pop();

        matrices.push();
        controller.applySpecialModelTransform(SpecialModels.FN_FAL.getModel(), FNFALAnimationController.INDEX_BOLT,transformType,matrices);

        Gun gun = ((GunItem) stack.getItem()).getGun();
        float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());
        if(controller.getAnimationFromLabel(GunAnimationController.AnimationLabel.DRAW).equals(controller.getPreviousAnimation())) cooldownOg = 1;
        if(transformType.isFirstPerson()) {
            AnimationMeta reloadEmpty = controller.getAnimationFromLabel(GunAnimationController.AnimationLabel.RELOAD_EMPTY);
            boolean shouldOffset = reloadEmpty != null && reloadEmpty.equals(controller.getPreviousAnimation()) && controller.isAnimationRunning();
            if (Gun.hasAmmo(stack) || shouldOffset) {
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.translate(0, 0, 0.135f * (-4.5 * Math.pow(cooldownOg - 0.5, 2) + 1.0));
            } else if (!Gun.hasAmmo(stack)) {
                {
                    matrices.translate(0, 0, 0.135f * (-4.5 * Math.pow(0.5 - 0.5, 2) + 1.0));
                }
            }
        }
        RenderUtil.renderModel(SpecialModels.FN_FAL_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.pop();

        PlayerHandAnimation.render(controller,transformType,matrices,renderBuffer,light);
    }
}
