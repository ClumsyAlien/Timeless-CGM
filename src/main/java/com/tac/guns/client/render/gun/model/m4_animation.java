package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.animation.M4AnimationController;
import com.tac.guns.client.render.animation.module.GunAnimationController;
import com.tac.guns.client.render.animation.module.PlayerHandAnimation;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.init.ModItems;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import com.tac.guns.util.GunModifierHelper;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Timeless Development, and associates.
 */
public class m4_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay)
    {
        M4AnimationController controller = M4AnimationController.getInstance();
        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.M4_BODY.getModel(), M4AnimationController.INDEX_BODY, transformType, matrices);
            if (Gun.getScope(stack) == null) {
                RenderUtil.renderModel(SpecialModels.M4_CARRY.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.LIGHT_STOCK.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_LIGHT_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.TACTICAL_STOCK.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_TACTICAL_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.WEIGHTED_STOCK.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_H_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            // If niether trips, render the cover for the side or top, since only one is accessible at once currently, TODO: Have a more streamlined system to handle multi-accesible attachments
            if (Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack).getItem() == ModItems.BASIC_LASER.get()) {
                RenderUtil.renderLaserModuleModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2_L_RAIL.getModel(), stack, matrices, renderBuffer, light, overlay);
                RenderUtil.renderLaserModuleModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2_T_COVER.getModel(), stack, matrices, renderBuffer, light, overlay);

                RenderUtil.renderLaserModuleModel(SpecialModels.M4_B_LASER_DEVICE.getModel(), Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack), matrices, renderBuffer, light, overlay);
                RenderUtil.renderLaserModuleModel(SpecialModels.M4_B_LASER.getModel(), Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack), matrices, renderBuffer, 15728880, overlay); // 15728880 For fixed max light
            }
            else if (Gun.getAttachment(IAttachment.Type.IR_DEVICE, stack) != ItemStack.EMPTY && Gun.getAttachment(IAttachment.Type.IR_DEVICE, stack).getItem() == ModItems.IR_LASER.get()) {
                RenderUtil.renderLaserModuleModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2_T_RAIL.getModel(), stack, matrices, renderBuffer, light, overlay);
                RenderUtil.renderLaserModuleModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2_L_COVER.getModel(), stack, matrices, renderBuffer, light, overlay);

                RenderUtil.renderLaserModuleModel(SpecialModels.M4_IR_LASER_DEVICE.getModel(), Gun.getAttachment(IAttachment.Type.IR_DEVICE, stack), matrices, renderBuffer, light, overlay);
                if(transformType.isFirstPerson()) {
                    RenderUtil.renderLaserModuleModel(SpecialModels.M4_IR_LASER.getModel(), Gun.getAttachment(IAttachment.Type.IR_DEVICE, stack), matrices, renderBuffer, 15728880, overlay); // 15728880 For fixed max light
                }
            }

            if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack) == ItemStack.EMPTY && Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack) == ItemStack.EMPTY && Gun.getAttachment(IAttachment.Type.IR_DEVICE, stack) == ItemStack.EMPTY)
            {
                RenderUtil.renderModel(SpecialModels.M4_DEFAULT_HANDGUARD.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else
            {
                if(Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack) == ItemStack.EMPTY && Gun.getAttachment(IAttachment.Type.IR_DEVICE, stack) == ItemStack.EMPTY) {
                    RenderUtil.renderLaserModuleModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2_L_COVER.getModel(), stack, matrices, renderBuffer, light, overlay);
                    RenderUtil.renderLaserModuleModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2_T_COVER.getModel(), stack, matrices, renderBuffer, light, overlay);
                }
                RenderUtil.renderModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.LIGHT_GRIP.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_L_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);

            } else if (Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.SPECIALISED_GRIP.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_T_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.SILENCER.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_SUPPRESSOR.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_COMPENSATOR.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_COMPENSATOR.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_BRAKE.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.M4_BRAKE.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            else{
                RenderUtil.renderModel(SpecialModels.M4_DEFAULT_BARREL.getModel(), stack, matrices, renderBuffer, light, overlay);
            }

            RenderUtil.renderModel(SpecialModels.M4_SIGHT_LIGHT.getModel(), stack, matrices, renderBuffer, 15728880, overlay);
            RenderUtil.renderModel(SpecialModels.M4_BODY.getModel(), stack, matrices, renderBuffer, light, overlay);

            matrices.push();
            {
                if(transformType.isFirstPerson()) {
                    Gun gun = ((GunItem) stack.getItem()).getGun();
                    float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());

                    if (Gun.hasAmmo(stack)) {
                        // Math provided by Bomb787 on GitHub and Curseforge!!!
                        matrices.translate(0, 0, 0.185f * (-4.5 * Math.pow(cooldownOg - 0.5, 2) + 1.0));
                    } else if (!Gun.hasAmmo(stack)) {
                        {
                            matrices.translate(0, 0, 0.185f * (-4.5 * Math.pow(0.5 - 0.5, 2) + 1.0));
                        }
                    }
                }
                RenderUtil.renderModel(SpecialModels.M4_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            matrices.pop();
        }
        matrices.pop();

        matrices.push();
        {
            controller.applySpecialModelTransform(SpecialModels.M4_BODY.getModel(), M4AnimationController.INDEX_MAGAZINE, transformType, matrices);

            if (GunModifierHelper.getAmmoCapacityWeight(stack) > -1) {
                RenderUtil.renderModel(SpecialModels.M4_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else {
                RenderUtil.renderModel(SpecialModels.M4_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
        }
        matrices.pop();
        PlayerHandAnimation.render(controller,transformType,matrices,renderBuffer,light);
    }
}
