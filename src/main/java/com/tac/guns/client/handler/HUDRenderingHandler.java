package com.tac.guns.client.handler;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tac.guns.Reference;
import com.tac.guns.common.Gun;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.util.GunEnchantmentHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class HUDRenderingHandler extends AbstractGui {
    private static HUDRenderingHandler instance;

    private static final ResourceLocation[] AMMO_ICONS = new ResourceLocation[]
            {
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/counterassule_rifle.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/counterlmg.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/counterpistol.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/countershotgun.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/countersmg.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/countersniper.png")
            };

    private static final ResourceLocation[] FIREMODE_ICONS = new ResourceLocation[]
            {
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/safety.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/semi.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/full.png")
            };

    public static HUDRenderingHandler get() {
        return instance == null ? instance = new HUDRenderingHandler() : instance;
    }

    private HUDRenderingHandler() {
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (Minecraft.getInstance().player == null || !(Minecraft.getInstance().player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GunItem)) {
            return;
        }
        ClientPlayerEntity player = Minecraft.getInstance().player;
        ItemStack heldItem = player.getHeldItemMainhand();
        Gun gun = ((TimelessGunItem) heldItem.getItem()).getGun();
        MatrixStack stack = event.getMatrixStack();
        float anchorPointX = event.getWindow().getScaledWidth() / 12F * 11F;
        float anchorPointY = event.getWindow().getScaledHeight() / 10F * 9F;

        float iconSize = 64.0F;
        float fireModeSize = 32.0F;

        // Weapon icon rendering
        RenderSystem.enableAlphaTest();
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        stack.push();
        {
            stack.translate(anchorPointX , anchorPointY , 0);
            stack.translate(-iconSize, -iconSize, 0);

            if (gun.getDisplay().getWeaponType() > 5 || gun.getDisplay().getWeaponType() < 0)
                Minecraft.getInstance().getTextureManager().bindTexture(AMMO_ICONS[0]);
            else
                Minecraft.getInstance().getTextureManager().bindTexture(AMMO_ICONS[gun.getDisplay().getWeaponType()]);

            Matrix4f matrix = stack.getLast().getMatrix();
            buffer.pos(matrix, 0, iconSize, 0).tex(0, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.pos(matrix, iconSize, iconSize, 0).tex(1, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.pos(matrix, iconSize, 0, 0).tex(1, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.pos(matrix, 0, 0, 0).tex(0, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
        }
        stack.pop();
        buffer.finishDrawing();
        WorldVertexBufferUploader.draw(buffer);

        // FireMode rendering
        RenderSystem.enableAlphaTest();
        buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        stack.push();
        {
            stack.translate(anchorPointX - iconSize / 4F, anchorPointY - iconSize/ 5F * 3F, 0);
            stack.translate(-fireModeSize, -fireModeSize, 0);

            int fireMode = Objects.requireNonNull(player.getHeldItemMainhand().getTag()).getInt("CurrentFireMode");
            if(player.getHeldItemMainhand().getTag().get("CurrentFireMode") == null)
                Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[gun.getGeneral().getRateSelector()[0]]);
            else if(fireMode > 2 || fireMode < 0)
                Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[0]);
            else
                Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[fireMode]);

            Matrix4f matrix = stack.getLast().getMatrix();
            buffer.pos(matrix, 0, fireModeSize, 0).tex(0, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.pos(matrix, fireModeSize, fireModeSize, 0).tex(1, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.pos(matrix, fireModeSize, 0, 0).tex(1, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.pos(matrix, 0, 0, 0).tex(0, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
        }
        stack.pop();
        buffer.finishDrawing();
        WorldVertexBufferUploader.draw(buffer);

        // Text rendering
        stack.push();
        {
            stack.translate(anchorPointX - iconSize/2 , anchorPointY - iconSize / 4, 0);
            String text = player.getHeldItemMainhand().getTag().getInt("AmmoCount") +" / " + GunEnchantmentHelper.getAmmoCapacity(heldItem, gun);
            stack.scale(2f, 2f , 2f);
            drawCenteredString(stack, Minecraft.getInstance().fontRenderer, text, 0, 0, 0xffffff);
        }
        stack.pop();
    }
}