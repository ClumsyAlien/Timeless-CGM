package com.tac.guns.client.handler;

import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tac.guns.Config;
import com.tac.guns.Reference;
import com.tac.guns.client.handler.command.ObjectRenderEditor;
import com.tac.guns.common.Gun;
import com.tac.guns.common.ReloadTracker;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class HUDRenderingHandler extends GuiComponent {
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

    private static final ResourceLocation[] FIREMODE_ICONS_OLD = new ResourceLocation[]
            {
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/safety.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/semi.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/full.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/burst.png"),
            };
    private static final ResourceLocation[] FIREMODE_ICONS = new ResourceLocation[]
            {
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/firemode_safety.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/firemode_semi.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/firemode_auto.png"),
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/firemode_burst.png"),
            };
    private static final ResourceLocation[] RELOAD_ICONS = new ResourceLocation[]
            {
                    new ResourceLocation(Reference.MOD_ID, "textures/gui/reloadbar.png")
            };

    public static HUDRenderingHandler get() {
        return instance == null ? instance = new HUDRenderingHandler() : instance;
    }

    private HUDRenderingHandler() {
    }

    private int ammoReserveCount = 0;
    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent e)
    {
        if (e.phase != TickEvent.Phase.END)
            return;
        if(Minecraft.getInstance().player == null)
            return;
        if(Minecraft.getInstance().player.getMainHandItem().getItem() instanceof GunItem)
        {
            GunItem gunItem = (GunItem) Minecraft.getInstance().player.getMainHandItem().getItem();
            this.ammoReserveCount = ReloadTracker.calcMaxReserveAmmo(Gun.findAmmo(Minecraft.getInstance().player, gunItem.getGun().getProjectile().getItem()));
        }
    }
    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (Minecraft.getInstance().player == null || !(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TimelessGunItem)) {
            return;
        }
        if(!Config.CLIENT.weaponGUI.weaponGui.get()) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        ItemStack heldItem = player.getMainHandItem();
        TimelessGunItem gunItem = (TimelessGunItem) heldItem.getItem();
        Gun gun = gunItem.getGun();
        PoseStack stack = event.getMatrixStack();
        float anchorPointX = event.getWindow().getGuiScaledWidth() / 12F * 11F;
        float anchorPointY = event.getWindow().getGuiScaledHeight() / 10F * 9F;

        float configScaleWeaponCounter = Config.CLIENT.weaponGUI.weaponAmmoCounter.weaponAmmoCounterSize.get().floatValue();
        float configScaleWeaponFireMode = Config.CLIENT.weaponGUI.weaponFireMode.weaponFireModeSize.get().floatValue();
        float configScaleWeaponReloadBar = Config.CLIENT.weaponGUI.weaponReloadTimer.weaponReloadTimerSize.get().floatValue();

        float counterSize = 1.8F * configScaleWeaponCounter;
        float fireModeSize = 32.0F * configScaleWeaponFireMode;
        float ReloadBarSize = 32.0F * configScaleWeaponReloadBar;

        BufferBuilder buffer;
        if(Config.CLIENT.weaponGUI.weaponFireMode.showWeaponFireMode.get()) {
            // FireMode rendering
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            buffer = Tesselator.getInstance().getBuilder();
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            stack.pushPose();
            {
                stack.translate(anchorPointX - (fireModeSize*2) / 4F, anchorPointY - (fireModeSize*2) / 5F * 3F, 0);
                stack.translate(-fireModeSize + (-62.7) + (-Config.CLIENT.weaponGUI.weaponFireMode.x.get().floatValue()), -fireModeSize + 52.98 + (-Config.CLIENT.weaponGUI.weaponFireMode.y.get().floatValue()), 0);

                stack.translate(20, 5, 0);
                int fireMode;

                if(player.getMainHandItem().getTag() == null)
                    fireMode = gun.getGeneral().getRateSelector()[0];
                else if(player.getMainHandItem().getTag().getInt("CurrentFireMode") == 0)
                    fireMode = gun.getGeneral().getRateSelector()[0];
                else
                    fireMode = Objects.requireNonNull(player.getMainHandItem().getTag()).getInt("CurrentFireMode");
                //int fireMode = gunItem.getSupportedFireModes()[gunItem.getCurrFireMode()];
                if (!Config.COMMON.gameplay.safetyExistence.get() && fireMode == 0)
                    RenderSystem.setShaderTexture(0, FIREMODE_ICONS[0]); // Render true firemode
                else
                    RenderSystem.setShaderTexture(0, FIREMODE_ICONS[fireMode]); // Render true firemode

                Matrix4f matrix = stack.last().pose();
                buffer.vertex(matrix, 0, fireModeSize/2, 0).uv(0, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
                buffer.vertex(matrix, fireModeSize/2, fireModeSize/2, 0).uv(1, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
                buffer.vertex(matrix, fireModeSize/2, 0, 0).uv(1, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
                buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            }
            stack.popPose();
            buffer.end();
            BufferUploader.end(buffer);
        }
        if(Config.CLIENT.weaponGUI.weaponAmmoCounter.showWeaponAmmoCounter.get()) {
            // Text rendering
            stack.pushPose();
            {
                stack.translate(
                    (anchorPointX - (counterSize*32) / 2) + (-Config.CLIENT.weaponGUI.weaponAmmoCounter.x.get().floatValue()),
                    (anchorPointY - (counterSize*32) / 4) + (-Config.CLIENT.weaponGUI.weaponAmmoCounter.y.get().floatValue()),
                    0
            );
            if(player.getMainHandItem().getTag() != null) {
                MutableComponent currentAmmo;
                MutableComponent reserveAmmo;
                int ammo = player.getMainHandItem().getTag().getInt("AmmoCount");
                if (player.getMainHandItem().getTag().getInt("AmmoCount") <= gun.getReloads().getMaxAmmo() / 4 && this.ammoReserveCount <= gun.getReloads().getMaxAmmo()) {
                    currentAmmo = byPaddingZeros(ammo).append(new TranslatableComponent("" + ammo)).withStyle(ChatFormatting.RED);
                    reserveAmmo =
                            byPaddingZeros(this.ammoReserveCount > 10000 ? 10000 : this.ammoReserveCount).append(new TranslatableComponent("" + (this.ammoReserveCount > 10000 ? 9999 : this.ammoReserveCount))).withStyle(ChatFormatting.RED);
                } else if (this.ammoReserveCount <= gun.getReloads().getMaxAmmo()) {
                    currentAmmo = byPaddingZeros(ammo).append(new TranslatableComponent("" + ammo).withStyle(ChatFormatting.WHITE));
                    reserveAmmo = byPaddingZeros(this.ammoReserveCount > 10000 ? 10000 : this.ammoReserveCount).append(new TranslatableComponent("" + (this.ammoReserveCount > 10000 ? 9999 : this.ammoReserveCount))).withStyle(ChatFormatting.RED);
                } else if (player.getMainHandItem().getTag().getInt("AmmoCount") <= gun.getReloads().getMaxAmmo() / 4) {
                    currentAmmo = byPaddingZeros(ammo).append(new TranslatableComponent("" + ammo)).withStyle(ChatFormatting.RED);
                    reserveAmmo = byPaddingZeros(this.ammoReserveCount > 10000 ? 10000 : this.ammoReserveCount).append(new TranslatableComponent("" + (this.ammoReserveCount > 10000 ? 9999 : this.ammoReserveCount))).withStyle(ChatFormatting.GRAY);
                } else {
                    currentAmmo = byPaddingZeros(ammo).append(new TranslatableComponent("" + ammo).withStyle(ChatFormatting.WHITE));
                    reserveAmmo = byPaddingZeros(this.ammoReserveCount > 10000 ? 10000 : this.ammoReserveCount).append(new TranslatableComponent("" + (this.ammoReserveCount > 10000 ? 9999 : this.ammoReserveCount))).withStyle(ChatFormatting.GRAY);
                }
                stack.scale(counterSize, counterSize, counterSize);
                stack.pushPose();
                {
                    stack.translate(-21.15, 0, 0 );
                    drawString(stack, Minecraft.getInstance().font, currentAmmo, 0, 0, 0xffffff); // Gun ammo
                }
                stack.popPose();

                stack.pushPose();
                {
                    stack.scale(0.7f, 0.7f, 0.7f);
                    stack.translate(
                            (3.7),
                            (3.4),
                            0 );
                    drawString(stack, Minecraft.getInstance().font, reserveAmmo, 0, 0, 0xffffff); // Reserve ammo
                }
                stack.popPose();
                }
            }
            stack.popPose();

            stack.pushPose();
            buffer = Tesselator.getInstance().getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            stack.translate(anchorPointX - (ReloadBarSize*4.35) / 4F, anchorPointY + (ReloadBarSize*1.625F) / 5F * 3F, 0);//stack.translate(anchorPointX - (fireModeSize*6) / 4F, anchorPointY - (fireModeSize*1F) / 5F * 3F, 0); // *68for21F
            stack.translate(-ReloadBarSize, -ReloadBarSize, 0);

            stack.translate(-16.25-7.3, 0.15+1.6, 0);
            // stack.translate(0, 0, );
            stack.scale(3.05F,0.028F,0); // *21F
            RenderSystem.setShaderTexture(0, RELOAD_ICONS[0]); // Future options to render bar types

            Matrix4f matrix = stack.last().pose();
            buffer.vertex(matrix, 0, ReloadBarSize, 0).uv(0, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.vertex(matrix, ReloadBarSize, ReloadBarSize, 0).uv(1, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.vertex(matrix, ReloadBarSize, 0, 0).uv(1, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();

            stack.translate(19.25, (1.5+(-63.4))*10, 0);
            // stack.translate(0, 0, );
            stack.scale(0.0095F,20.028F,0); // *21F

            buffer.vertex(matrix, 0, ReloadBarSize, 0).uv(0, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.vertex(matrix, ReloadBarSize, ReloadBarSize, 0).uv(1, 1).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.vertex(matrix, ReloadBarSize, 0, 0).uv(1, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();
            buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 0.99F).endVertex();

            buffer.end();
            BufferUploader.end(buffer);
            stack.popPose();
        }
    }

    private static MutableComponent byPaddingZeros(int number) {
        String text = String.format("%0" + (byPaddingZerosCount(number)+1) + "d", 1);
        text = text.substring(0, text.length()-1);
        return new TranslatableComponent(text).withStyle(ChatFormatting.GRAY);
    }
    private static int byPaddingZerosCount(int length) {
        if(length < 10)
            return 2;
        if(length < 100)
            return 1;
        if(length < 1000)
            return 0;
        return 0;
    }
}