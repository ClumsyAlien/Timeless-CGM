package com.tac.guns.client.render.crosshair;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tac.guns.client.handler.AimingHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

public class DynamicScalingTexturedCrosshair extends TexturedCrosshair implements IDynamicScalable{
    private final float initial = 0.65f;
    private final float horizontal = 1.2f;
    private final float vertical = 1.6f;
    private float scale = initial;
    private float prevScale = initial;

    public DynamicScalingTexturedCrosshair(ResourceLocation id) {
        super(id);
    }
    public DynamicScalingTexturedCrosshair(ResourceLocation id, boolean blend){
        super(id,blend);
    }

    @Override
    public void scale(float value) {
        this.prevScale = scale;
        this.scale = value;
    }

    @Override
    public float getInitialScale() {
        return initial;
    }

    @Override
    public float getHorizontalMovementScale() {
        return horizontal;
    }

    @Override
    public float getVerticalMovementScale() {
        return vertical;
    }

    @Override
    public void render(Minecraft mc, MatrixStack stack, int windowWidth, int windowHeight, float partialTicks){
        float alpha = 1.0F - (float) AimingHandler.get().getNormalisedAdsProgress();
        float size = 8.0F;

        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        BufferBuilder buffer = Tessellator.getInstance().getBuilder();

        stack.pushPose();
        {
            Matrix4f matrix = stack.last().pose();
            stack.translate(windowWidth / 2F, windowHeight / 2F, 0);
            float scale = 1F + MathHelper.lerp(partialTicks, this.prevScale, this.scale);
            stack.scale(scale, scale, scale);
            stack.translate(-size / 2F, -size / 2F, 0);
            mc.getTextureManager().bind(this.texture);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            buffer.vertex(matrix, 0, size, 0).uv(0, 1).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
            buffer.vertex(matrix, size, size, 0).uv(1, 1).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
            buffer.vertex(matrix, size, 0, 0).uv(1, 0).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
            buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
            buffer.end();
            RenderSystem.enableAlphaTest();
            WorldVertexBufferUploader.end(buffer);
        }
        stack.popPose();
    }
}
