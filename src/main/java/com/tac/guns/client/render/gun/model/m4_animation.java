package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.Config;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.render.gun.ModelOverrides;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.init.ModItems;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemCooldowns;
import com.mojang.math.Vector3f;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Timeless Development, and associates.
 */
public class m4_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrices, MultiBufferSource renderBuffer, int light, int overlay)
    {
        if(ModelOverrides.hasModel(stack) && transformType.equals(ItemTransforms.TransformType.GUI) && Config.CLIENT.quality.reducedGuiWeaponQuality.get())
        {
            matrices.pushPose();
            matrices.mulPose(Vector3f.XP.rotationDegrees(-60.0F));
            matrices.mulPose(Vector3f.YP.rotationDegrees(225.0F));
            matrices.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
            matrices.translate(0.9,0,0);
            matrices.scale(1.5F,1.5F,1.5F);
            RenderUtil.renderModel(stack, stack, matrices, renderBuffer, light, overlay);
            matrices.popPose();
            return;
        }
        
        if(Gun.getScope(stack) == null)
        {
            RenderUtil.renderModel(SpecialModels.M4_CARRY.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.LIGHT_STOCK.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_LIGHT_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.TACTICAL_STOCK.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_TACTICAL_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.WEIGHTED_STOCK.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_H_STOCK.getModel(), stack, matrices, renderBuffer, light, overlay);
        }

        if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack) == ItemStack.EMPTY && Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack) == ItemStack.EMPTY)
        {
            RenderUtil.renderModel(SpecialModels.M4_DEFAULT_HANDGUARD.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else
        {
            // Use when more rail implementations are ready.
            //RenderUtil.renderModel(SpecialModels.M4_EXTENDED_HANDGUARD.getModel(), stack, matrices, renderBuffer, light, overlay);
            RenderUtil.renderModel(SpecialModels.M4_EXTENDED_HANDGUARD_V2.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.LIGHT_GRIP.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_L_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);

        }
        else if(Gun.getAttachment(IAttachment.Type.UNDER_BARREL, stack).getItem() == ModItems.SPECIALISED_GRIP.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_T_GRIP.getModel(), stack, matrices, renderBuffer, light, overlay);
        }

        /*if(Gun.getAttachment(IAttachment.Type.SIDE_RAIL, stack).getItem() == ModItems.STANDARD_FLASHLIGHT.orElse(ItemStack.EMPTY.getItem()))
        {
            //matrices.push();
            matrices.translate(0, 0, 0.15f);
            RenderUtil.renderModel(SpecialModels.AR_15_CQB_STANDARD_FLASHLIGHT.getModel(), stack, matrices, renderBuffer, light, overlay);
            matrices.translate(0, 0, -0.15f);
            //matrices.pop();
        }*/

        if(Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.SILENCER.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_SUPPRESSOR.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else if(Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_COMPENSATOR.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_COMPENSATOR.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else if(Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.MUZZLE_BRAKE.orElse(ItemStack.EMPTY.getItem()))
        {
            RenderUtil.renderModel(SpecialModels.M4_BRAKE.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.OVER_CAPACITY.get(), stack) > 0)
        {
            RenderUtil.renderModel(SpecialModels.M4_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else
        {
            RenderUtil.renderModel(SpecialModels.M4_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
        }

        RenderUtil.renderModel(SpecialModels.M4_BODY.getModel(), stack, matrices, renderBuffer, light, overlay);
        //RenderUtil.renderModel(SpecialModels.M4_BOLT_HANDLE.getModel(), stack, matrices, renderBuffer, light, overlay);

        matrices.pushPose();
        ItemCooldowns tracker = Minecraft.getInstance().player.getCooldowns();
        float cooldownOg = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());

        if(Gun.hasAmmo(stack))
        {
            // Math provided by Bomb787 on GitHub and Curseforge!!!
            matrices.translate(0, 0, 0.185f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
        }
        else if(!Gun.hasAmmo(stack))
        {
            if(cooldownOg > 0.5){
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.translate(0, 0, 0.185f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
            }
            else
            {
                matrices.translate(0, 0, 0.185f * (-4.5 * Math.pow(0.5-0.5, 2) + 1.0));
            }
        }

        RenderUtil.renderModel(SpecialModels.M4_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.popPose();
    }
}
