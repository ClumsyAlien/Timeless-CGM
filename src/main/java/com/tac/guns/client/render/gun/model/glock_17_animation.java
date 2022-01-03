package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.render.animation.Animations;
import com.tac.guns.client.render.animation.Glock17AnimationController;
import com.tac.guns.client.render.animation.GunAnimationController;
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
public class glock_17_animation implements IOverrideModel {

    //The render method, similar to what is in DartEntity. We can render the item
    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {

        //matrices.translate(0.01, 0.1, -0.1);
        //matrices.rotate(Vector3f.YP.rotationDegrees(-0.5F));

        /*
            // So this area will be tested for the item specific name, allowing the use of custom attachments
        if(Gun.getAttachment(IAttachment.Type.BARREL,stack).getItem().getName() != "")
        {
            RenderUtil.renderModel(SpecialModels.GLOCK_17_SUPPRESSOR_OVERIDE.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        */
        boolean isFirstPerson = transformType.isFirstPerson();

        // Apply animation for magazine
        if(isFirstPerson) Animations.pushNode(Glock17AnimationController.getInstance().animationRunning(), Glock17AnimationController.INDEX_MAGAZINE);
        {
            if (EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.OVER_CAPACITY.get(), entity) > 0) {
                RenderUtil.renderModel(SpecialModels.GLOCK_17_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            } else {
                RenderUtil.renderModel(SpecialModels.GLOCK_17_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            }
        }
        if(isFirstPerson) Animations.popNode(); //always pop
        // Apply animation for main body and attachments
        if(isFirstPerson) Animations.pushNode(Glock17AnimationController.getInstance().animationRunning(), Glock17AnimationController.INDEX_BODY);{
            if(Gun.getAttachment(IAttachment.Type.BARREL,stack).getItem() == ModItems.SILENCER.get())
            {
                RenderUtil.renderModel(SpecialModels.GLOCK_17_SUPPRESSOR_OVERIDE.getModel(), stack, matrices, renderBuffer, light, overlay);
                //Gun.getAttachment(IAttachment.Type.BARREL, GunItem.getItemById(Item.getIdFromItem(stack.getItem())).getDefaultInstance())
            }
            RenderUtil.renderModel(SpecialModels.GLOCK_17.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(isFirstPerson) Animations.popNode(); //always pop
            //Always push
            matrices.push();

            CooldownTracker tracker = Minecraft.getInstance().player.getCooldownTracker();
        float cooldownOg = tracker.getCooldown(stack.getItem(), Minecraft.getInstance().getRenderPartialTicks());

        MatrixStack extraMatrixStack = Animations.getExtraMatrixStack();
        extraMatrixStack.push();
        float alpha1 = (float) (0.175f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
        if(Animations.isAnimationRunning(Glock17AnimationController.getInstance().animationRunning())){
            extraMatrixStack.translate(0, 0, alpha1);
            extraMatrixStack.translate(0,0,0.1875F);
        }else if(Gun.hasAmmo(stack))
        {
            // Math provided by Bomb787 on GitHub and Curseforge!!!
            extraMatrixStack.translate(0, 0, alpha1);
        }
        else if(!Gun.hasAmmo(stack))
        {
            if(cooldownOg > 0.5){
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                extraMatrixStack.translate(0, 0, alpha1);
            }
            else
            {
                extraMatrixStack.translate(0, 0, 0.175f * (-4.5 * Math.pow(0.5-0.5, 2) + 1.0));
            }
        }
        extraMatrixStack.translate(0.00f, 0.0f, -0.025f);

        if(isFirstPerson) Animations.pushNode(Glock17AnimationController.getInstance().animationRunning(), Glock17AnimationController.INDEX_SLIDE);
        {
            RenderUtil.renderModel(SpecialModels.GLOCK_17_SLIDE.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        if(isFirstPerson) Animations.popNode();
        extraMatrixStack.pop();

            //Always pop
            matrices.pop();
    }

     

    //TODO comments
}
