package com.tac.guns.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.handler.BulletTrailRenderingHandler;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(WorldRenderer.class)
public class WorldRendererMixin
{
    //@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;checkPoseStack(Lcom/mojang/blaze3d/matrix/MatrixStack;)V", ordinal = 0))
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;checkPoseStack(Lcom/mojang/blaze3d/matrix/MatrixStack;)V", ordinal = 0), method="renderLevel")
    private void renderBullets(MatrixStack stack, float partialTicks, long finishTimeNano, boolean drawBlockOutline, ActiveRenderInfo info, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projection, CallbackInfo ci)
    {
        BulletTrailRenderingHandler.get().render(stack, partialTicks);
    }
}
