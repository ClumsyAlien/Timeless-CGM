package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.render.animation.Ak47AnimationController;
import com.tac.guns.client.render.animation.Animations;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModItems;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
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
public class ak47_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {
        boolean isFirstPerson = transformType.isFirstPerson();
        MatrixStack extraMatrixStack = Animations.getExtraMatrixStack();
        if(isFirstPerson)Animations.pushNode(Ak47AnimationController.getInstance().animationRunning(), Ak47AnimationController.INDEX_BODY);
        {
            if (Gun.getScope(stack) != null) {
                RenderUtil.renderModel(SpecialModels.AK47_OPTIC_MOUNT.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.LIGHT_STOCK.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.AK47_BUTT_LIGHTWEIGHT.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.TACTICAL_STOCK.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.AK47_BUTT_TACTICAL.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.STOCK, stack).getItem() == ModItems.WEIGHTED_STOCK.orElse(ItemStack.EMPTY.getItem())) {
                RenderUtil.renderModel(SpecialModels.AK47_BUTT_HEAVY.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
            if (Gun.getAttachment(IAttachment.Type.BARREL, stack).getItem() == ModItems.SILENCER.orElse(ItemStack.EMPTY.getItem())) {
                //int overlayTmp = Gun.getAttachment(IAttachment.Type.BARREL, stack).getStack().serializeNBT().getInt("Color");
                //int overlayTmp = Minecraft.getInstance().getItemColors().getColor(Gun.getAttachment(IAttachment.Type.BARREL, stack).getStack(), 0);
                //if(overlayTmp == -1)
                //{
                //    overlayTmp = overlay;
                //}
            /*
                Hm, it seems like the getAttachment().stack() method chain does not actually grab the color of the specific attachment
                I will be making a bug report as I don't think this behavior is correct and something wrong is on either side as this should be clearly possible
            */
                matrices.push();
                extraMatrixStack.push();
                extraMatrixStack.translate(0,0,-0.45);
                RenderUtil.renderModel(SpecialModels.AK47_SILENCER.getModel(), stack, matrices, renderBuffer, light, overlay);
                extraMatrixStack.pop();
                matrices.pop();
            }
            RenderUtil.renderModel(SpecialModels.AK47.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(isFirstPerson) Animations.popNode();

        //Always push
        matrices.push();
        //We're getting the cooldown tracker for the item - items like the sword, ender pearl, and chorus fruit all have this too.
        CooldownTracker tracker = Minecraft.getInstance().player.getCooldownTracker();
        float cooldownOg = tracker.getCooldown(stack.getItem(), Minecraft.getInstance().getRenderPartialTicks());
        // Math provided by Bomb787 on GitHub and Curseforge!!!
        double boltTransitionValue = 0.190f * (-4.5 * Math.pow(cooldownOg - 0.5, 2) + 1);
        //if(!isFirstPerson) matrices.translate(0, 0, boltTransitionValue);
        extraMatrixStack.push();
        extraMatrixStack.translate(0,0,boltTransitionValue);
        if(isFirstPerson)Animations.pushNode(Ak47AnimationController.getInstance().animationRunning(), Ak47AnimationController.INDEX_BOLT);
        {
            RenderUtil.renderModel(SpecialModels.AK47_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(isFirstPerson) Animations.popNode();
        extraMatrixStack.pop();
        //Always pop
        matrices.pop();

        if(isFirstPerson)Animations.pushNode(Ak47AnimationController.getInstance().animationRunning(), Ak47AnimationController.INDEX_MAGAZINE);
        {
            RenderUtil.renderModel(SpecialModels.AK47_MAGAZINE.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(isFirstPerson) Animations.popNode();
    }

     

    //TODO comments
}
