package com.tac.guns.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.Reference;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEffects;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.util.GunEnchantmentHelper;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    // Make Mixin resources/util file to house these puppies as public dictionaries type collections, expect people add new data since we simply iterate
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


    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/IProfiler;endStartSection(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    public void updateCameraAndRender(float partialTicks, long nanoTime, boolean renderWorldIn, CallbackInfo ci)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null) {
            return;
        }
        MainWindow window = Minecraft.getInstance().getMainWindow();
        EffectInstance blindedEffect = player.getActivePotionEffect(ModEffects.BLINDED.get());
        if (blindedEffect != null) {
            // Render white screen-filling overlay at full alpha effect when duration is above threshold
            // When below threshold, fade to full transparency as duration approaches 0
            float percent = Math.min((blindedEffect.getDuration() / (float) Config.SERVER.alphaFadeThreshold.get()), 1);
            AbstractGui.fill(new MatrixStack(), 0, 0, window.getWidth(), window.getHeight(), ((int) (percent * Config.SERVER.alphaOverlay.get() + 0.5) << 24) | 16777215);
        }

        ItemStack heldItem = player.getHeldItemMainhand();

        if(Config.CLIENT.display.weaponGui.get()) {
            if ((heldItem.getItem() instanceof GunItem)) {
                Gun gun = ((TimelessGunItem) heldItem.getItem()).getGun();

                // Ammunition rendering

                MatrixStack counterStack = new MatrixStack();

                float scaler = 1.0f;
                double guiScale = window.getGuiScaleFactor();

                if (guiScale == 1.0d)
                    scaler = 4.0f;
                else if (guiScale == 2.0d)
                    scaler = 2.0f;
                else if (guiScale == 3.0d)
                    scaler = 1.35f;

                int counterX = 310;
                int counterY = 182;

                counterStack.scale(scaler, scaler, scaler);
                counterStack.scale(1.25f, 1.25f, 1.25f);

                if (GunEnchantmentHelper.getAmmoCapacity(heldItem, gun) < 10)
                    counterX += 7;
                else if (GunEnchantmentHelper.getAmmoCapacity(heldItem, gun) > 99)
                    counterX -= 7;

                Minecraft.getInstance().fontRenderer.drawString(
                        counterStack,
                        player.getHeldItemMainhand().getTag().getInt("AmmoCount") + " / " + GunEnchantmentHelper.getAmmoCapacity(heldItem, gun),
                        counterX,
                        counterY,
                        16777215);

                // Weapon icon rendering

                MatrixStack counterIconStack = new MatrixStack();
                counterIconStack.scale(scaler, scaler, scaler);
                counterIconStack.scale(0.18f, 0.18f, 0.18f);

                if (gun.getDisplay().getWeaponType() > 5 || gun.getDisplay().getWeaponType() < 0)
                    Minecraft.getInstance().getTextureManager().bindTexture(AMMO_ICONS[0]);
                else
                    Minecraft.getInstance().getTextureManager().bindTexture(AMMO_ICONS[gun.getDisplay().getWeaponType()]);

                Minecraft.getInstance().ingameGUI.blit(counterIconStack, window.getWindowX() + 2150, window.getWindowX() + 1051, 0, 0, 256, 256);

                // FireMode rendering

                MatrixStack firemodeStack = new MatrixStack();
                firemodeStack.scale(scaler, scaler, scaler);
                firemodeStack.scale(0.08f, 0.08f, 0.08f);

                int fireMode = player.getHeldItemMainhand().getTag().getInt("CurrentFireMode");

                if (player.getHeldItemMainhand().getTag().get("CurrentFireMode") == null)
                    Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[gun.getGeneral().getRateSelector()[0]]);
                else if (fireMode > 2 || fireMode < 0) // Weapons with unsupported modes will render as "default"
                    Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[2]);
                if(!Config.COMMON.gameplay.safetyExistence.get() && fireMode == 0)
                    Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[fireMode+1]); // Render true firemode
                else
                    Minecraft.getInstance().getTextureManager().bindTexture(FIREMODE_ICONS[fireMode]); // Render true firemode


                Minecraft.getInstance().ingameGUI.blit(firemodeStack, window.getWindowX() + 5000, window.getWindowX() + 2301, 0, 0, 256, 256);
            }
        }
    }
}