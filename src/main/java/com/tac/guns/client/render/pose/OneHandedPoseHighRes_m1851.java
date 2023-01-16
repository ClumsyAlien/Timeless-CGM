package com.tac.guns.client.render.pose;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import com.mojang.math.Vector3f;

/**
 * Author: ClumsyAlien, codebase and design based off Mr.Crayfish's class concept
 */
public class OneHandedPoseHighRes_m1851 extends OneHandedPose {
	@Override
	public void renderFirstPersonArms(LocalPlayer player, HumanoidArm hand, ItemStack stack, PoseStack matrixStack, MultiBufferSource buffer, int light, float partialTicks) {
		matrixStack.translate(-0.161, -0.27, 0.85);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
		
		double centerOffset = 2.5;
		if (Minecraft.getInstance().player.getModelName().equals("slim")) {
			centerOffset += hand == HumanoidArm.RIGHT ? 0.2 : 0.8;
		}
		centerOffset = hand == HumanoidArm.RIGHT ? -centerOffset : centerOffset;
		if (Minecraft.getInstance().player.getModelName().equals("slim")) {
			matrixStack.translate(centerOffset * 0.0755, -0.45, 1.0); // (1)^
		} else {
			matrixStack.translate(centerOffset * 0.0625, -0.45, 1.0); // (1)^
		}
		
		matrixStack.mulPose(Vector3f.XP.rotationDegrees(90F));
		matrixStack.scale(1.5F, 1.5F, 1.5F);
		
		RenderUtil.renderFirstPersonArm(player, hand, matrixStack, buffer, light);
	}
}
