package com.tac.guns.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tac.guns.client.handler.BulletTrailRenderingHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.LevelRenderer;
import com.mojang.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
@Mixin(LevelRenderer.class)
public class WorldRendererMixin
{
    /*@Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;checkMatrixStack(Lcom/mojang/blaze3d/matrix/MatrixStack;)V", ordinal = 0))
    private void renderBullets(PoseStack stack, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera info, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projection, CallbackInfo ci) {
        //BulletTrailRenderingHandler.get().render(stack, partialTicks);
    }*/
}
