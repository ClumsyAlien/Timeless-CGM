package com.tac.guns.client.render.pose;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.handler.ReloadHandler;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.item.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import com.mojang.math.Vector3f;

/**
 * Author: ClumsyAlien, codebase and design based off Mr.Crayfish's class concept
 */
public class TwoHandedPoseHighRes_mosin extends TwoHandedPose {
	
	@Override
	public void renderFirstPersonArms(LocalPlayer player, HumanoidArm hand, ItemStack stack, PoseStack matrixStack, MultiBufferSource buffer, int light, float partialTicks) {
		matrixStack.translate(0, 0, -1);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
		
		matrixStack.pushPose();
		
		float reloadProgress = ReloadHandler.get().getReloadProgress(partialTicks, stack);
		matrixStack.translate(reloadProgress * 1.5, -reloadProgress, -reloadProgress * 1.5);
		
		int side = hand.getOpposite() == HumanoidArm.RIGHT ? 1 : -1;
		matrixStack.translate(7.075 * side * 0.0625, -0.76, -0.53);
		
		if (Minecraft.getInstance().player.getModelName().equals("slim") && hand.getOpposite() == HumanoidArm.LEFT) {
			matrixStack.translate(0.03125F * -side, 0, 0);
		}
		
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(80F));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(15F * -side));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(15F * -side));
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15F));
		matrixStack.scale(1.0F, 1.0F, 1.0F);
		
		RenderUtil.renderFirstPersonArm(player, hand.getOpposite(), matrixStack, buffer, light);
		
		matrixStack.popPose();
		
		double centerOffset = 2.5;
		if (Minecraft.getInstance().player.getModelName().equals("slim")) {
			centerOffset += hand == HumanoidArm.RIGHT ? 0.2 : 0.8;
		}
		centerOffset = hand == HumanoidArm.RIGHT ? -centerOffset : centerOffset;
		matrixStack.translate(centerOffset * 0.0315, -0.55, -1.575);
		
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(80F));
		matrixStack.scale(0.5F, 0.5F, 0.5F);
		
		RenderUtil.renderFirstPersonArm(player, hand, matrixStack, buffer, light);
	}
}
