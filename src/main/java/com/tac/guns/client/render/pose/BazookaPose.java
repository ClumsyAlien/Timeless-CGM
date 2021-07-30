package com.tac.guns.client.render.pose;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.common.GripType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Author: MrCrayfish
 */
public class BazookaPose extends WeaponPose
{
    @Override
    protected AimPose getUpPose()
    {
        AimPose pose = new AimPose();
        pose.getIdle().setRenderYawOffset(35F).setItemRotation(new Vector3f(10F, 0F, 0F)).setRightArm(new LimbPose().setRotationAngleX(-170F).setRotationAngleY(-35F).setRotationAngleZ(0F).setRotationPointY(4).setRotationPointZ(-2)).setLeftArm(new LimbPose().setRotationAngleX(-130F).setRotationAngleY(65F).setRotationAngleZ(0F).setRotationPointX(3).setRotationPointY(2).setRotationPointZ(1));
        return pose;
    }

    @Override
    protected AimPose getForwardPose()
    {
        AimPose pose = new AimPose();
        pose.getIdle().setRenderYawOffset(35F).setRightArm(new LimbPose().setRotationAngleX(-90F).setRotationAngleY(-35F).setRotationAngleZ(0F).setRotationPointY(2).setRotationPointZ(0)).setLeftArm(new LimbPose().setRotationAngleX(-91F).setRotationAngleY(35F).setRotationAngleZ(0F).setRotationPointX(4).setRotationPointY(2).setRotationPointZ(0));
        return pose;
    }

    @Override
    protected AimPose getDownPose()
    {
        AimPose pose = new AimPose();
        pose.getIdle().setRenderYawOffset(35F).setRightArm(new LimbPose().setRotationAngleX(-10F).setRotationAngleY(-35F).setRotationAngleZ(0F).setRotationPointY(2).setRotationPointZ(0)).setLeftArm(new LimbPose().setRotationAngleX(-10F).setRotationAngleY(15F).setRotationAngleZ(30F).setRotationPointX(4).setRotationPointY(2).setRotationPointZ(0));
        return pose;
    }

    @Override
    protected boolean hasAimPose()
    {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void applyPlayerModelRotation(PlayerEntity player, PlayerModel model, Hand hand, float aimProgress)
    {
        if(Config.CLIENT.display.oldAnimations.get())
        {
            boolean right = Minecraft.getInstance().options.mainHand == HandSide.RIGHT ? hand == Hand.MAIN_HAND : hand == Hand.OFF_HAND;
            ModelRenderer mainArm = right ? model.rightArm : model.leftArm;
            ModelRenderer secondaryArm = right ? model.leftArm : model.rightArm;
            mainArm.xRot = (float) Math.toRadians(-90F);
            mainArm.yRot = (float) Math.toRadians(-35F) * (right ? 1F : -1F);
            mainArm.zRot = (float) Math.toRadians(0F);
            secondaryArm.xRot = (float) Math.toRadians(-91F);
            secondaryArm.yRot = (float) Math.toRadians(45F) * (right ? 1F : -1F);
            secondaryArm.zRot = (float) Math.toRadians(0F);
        }
        else
        {
            super.applyPlayerModelRotation(player, model, hand, aimProgress);
        }
    }

    @Override
    public void applyPlayerPreRender(PlayerEntity player, Hand hand, float aimProgress, MatrixStack matrixStack, IRenderTypeBuffer buffer)
    {
        if(Config.CLIENT.display.oldAnimations.get())
        {
            boolean right = Minecraft.getInstance().options.mainHand == HandSide.RIGHT ? hand == Hand.MAIN_HAND : hand == Hand.OFF_HAND;
            player.yBodyRotO = player.yRotO + 35F * (right ? 1F : -1F);
            player.yBodyRot = player.yRot + 35F * (right ? 1F : -1F);
        }
        else
        {
            super.applyPlayerPreRender(player, hand, aimProgress, matrixStack, buffer);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void applyHeldItemTransforms(PlayerEntity player, Hand hand, float aimProgress, MatrixStack matrixStack, IRenderTypeBuffer buffer)
    {
        if(!Config.CLIENT.display.oldAnimations.get())
        {
            super.applyHeldItemTransforms(player, hand, aimProgress, matrixStack, buffer);
        }
    }

    @Override
    public boolean applyOffhandTransforms(PlayerEntity player, PlayerModel model, ItemStack stack, MatrixStack matrixStack, float partialTicks)
    {
        return GripType.applyBackTransforms(player, matrixStack);
    }

    @Override
    public boolean canApplySprintingAnimation()
    {
        return false;
    }
}
