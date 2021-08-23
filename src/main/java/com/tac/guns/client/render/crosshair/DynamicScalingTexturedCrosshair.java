package com.tac.guns.client.render.crosshair;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.util.GunModifierHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class DynamicScalingTexturedCrosshair extends TexturedCrosshair implements IDynamicScalable{
    private final float initial = 0.75f;
    private final float horizontal = 1.2f;
    private final float vertical = 1.6f;
    private float scale = initial;
    private float prevScale = initial;
    private int fractal = 4;

    public DynamicScalingTexturedCrosshair(ResourceLocation id) { super(id); }
    public DynamicScalingTexturedCrosshair(ResourceLocation id, boolean blend) { super(id,blend); }

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

    public int getFractal() { return fractal; }

    public void setFractal(int value) { if(value > 0) this.fractal = value; }

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
            stack.translate(windowWidth / 2F, windowHeight / 2F, 0);
            float scale = 1F + MathHelper.lerp(partialTicks, this.prevScale, this.scale);
            mc.getTextureManager().bind(this.texture);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            for(int f = 0;f < getFractal();f++){
                stack.pushPose();
                {
                    stack.mulPose(Vector3f.ZP.rotationDegrees(360F*f/getFractal()));
                    stack.translate(-size*scale / 2F, -size / 2F, 0);
                    Matrix4f matrix = stack.last().pose();
                    buffer.vertex(matrix, 0, size, 0).uv(0, 1).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                    buffer.vertex(matrix, size, size, 0).uv(1, 1).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                    buffer.vertex(matrix, size, 0, 0).uv(1, 0).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                    buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, alpha).endVertex();

                }
                stack.popPose();
            }
            buffer.end();
            RenderSystem.enableAlphaTest();
            WorldVertexBufferUploader.end(buffer);
        }
        stack.popPose();
    }

    public void tick() {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity playerEntity = mc.player;

        if(playerEntity == null) return;

        float scale = this.getInitialScale();
        TimelessGunItem gunItem;

        if(playerEntity.getMainHandItem().getItem() instanceof TimelessGunItem)
        {
            gunItem = (TimelessGunItem) playerEntity.getMainHandItem().getItem();

            if (playerEntity.getX() != playerEntity.xo || playerEntity.getZ() != playerEntity.zo)
                scale = this.getHorizontalMovementScale();
            if (playerEntity.getY() != playerEntity.yo) scale = this.getVerticalMovementScale();

            scale = scale * GunModifierHelper.getModifiedSpread(playerEntity.getMainHandItem(), gunItem.getGun().getGeneral().getSpread());
            this.scale(scale);
        }
    }
    @Override
    public void onGunFired()
    {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity playerEntity = mc.player;

        TimelessGunItem gunItem = (TimelessGunItem) playerEntity.getMainHandItem().getItem();
        float gunRecoil = GunModifierHelper.getRecoilModifier(playerEntity.getMainHandItem());

        // Calculating average Vertical and Horizontal recoil along with reducing modifier to a useful metric
        float recoil = ( ( (gunItem.getGun().getGeneral().getRecoilAngle() - gunRecoil) +
                (gunItem.getGun().getGeneral().getHorizontalRecoilAngle() - gunRecoil) ) / 10 )
                + 1;
        // The +1 is used to ensure we have a "Percentage", only for testing and may be reverted

        this.scale = scale * recoil;
    }
}
