package com.tac.guns.client.handler;

import com.tac.guns.Config;
import com.tac.guns.common.Gun;
import com.tac.guns.event.GunFireEvent;
import com.tac.guns.item.GunItem;
import com.tac.guns.util.GunModifierHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class RecoilHandler
{
    private static RecoilHandler instance;

    public static RecoilHandler get()
    {
        if(instance == null)
        {
            instance = new RecoilHandler();
        }
        return instance;
    }

    private Random random = new Random();
    private double gunRecoilNormal;
    private double gunRecoilAngle;
    private float gunRecoilRandom;
    private float cameraRecoil;
    private float progressCameraRecoil;

    private RecoilHandler() {}

    @SubscribeEvent
    public void onGunFire(GunFireEvent.Post event)
    {
        if(!event.isClient())
            return;

        if(!Config.SERVER.enableCameraRecoil.get())
            return;

        ItemStack heldItem = event.getStack();
        GunItem gunItem = (GunItem) heldItem.getItem();
        Gun modifiedGun = gunItem.getModifiedGun(heldItem);
        float recoilModifier = 1.0F - GunModifierHelper.getRecoilModifier(heldItem);
        recoilModifier *= this.getAdsRecoilReduction(modifiedGun);
        this.cameraRecoil = modifiedGun.getGeneral().getRecoilAngle() * recoilModifier;
        this.progressCameraRecoil = 0F;
        this.gunRecoilRandom = random.nextFloat();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        if(!Config.SERVER.enableCameraRecoil.get())
            return;

        if(event.phase != TickEvent.Phase.END || this.cameraRecoil <= 0)
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null)
            return;

        float recoilAmount = this.cameraRecoil * mc.getDeltaFrameTime() * 0.1F;
        float startProgress = this.progressCameraRecoil / this.cameraRecoil;
        float endProgress = (this.progressCameraRecoil + recoilAmount) / this.cameraRecoil;

        if(startProgress < 0.2F)
        {
            mc.player.xRot -= ((endProgress - startProgress) / 0.2F) * this.cameraRecoil;
        }
        else
        {
            mc.player.xRot += ((endProgress - startProgress) / 0.8F) * this.cameraRecoil;
        }

        this.progressCameraRecoil += recoilAmount;

        if(this.progressCameraRecoil >= this.cameraRecoil)
        {
            this.cameraRecoil = 0;
            this.progressCameraRecoil = 0;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderOverlay(RenderHandEvent event)
    {
        if(event.getHand() != Hand.MAIN_HAND)
            return;

        ItemStack heldItem = event.getItemStack();
        if(!(heldItem.getItem() instanceof GunItem))
            return;

        GunItem gunItem = (GunItem) heldItem.getItem();
        Gun modifiedGun = gunItem.getModifiedGun(heldItem);
        CooldownTracker tracker = Minecraft.getInstance().player.getCooldowns();
        float cooldown = tracker.getCooldownPercent(gunItem, Minecraft.getInstance().getFrameTime());
        cooldown = cooldown >= modifiedGun.getGeneral().getRecoilDurationOffset() ? (cooldown - modifiedGun.getGeneral().getRecoilDurationOffset()) / (1.0F - modifiedGun.getGeneral().getRecoilDurationOffset()) : 0.0F;
        if(cooldown >= 0.8)
        {
            float amount = 1.0F * ((1.0F - cooldown) / 0.2F);
            this.gunRecoilNormal = 1 - (--amount) * amount * amount * amount;
        }
        else
        {
            float amount = (cooldown / 0.8F);
            this.gunRecoilNormal = amount < 0.5 ? 2 * amount * amount : -1 + (4 - 2 * amount) * amount;
        }

        this.gunRecoilAngle = modifiedGun.getGeneral().getRecoilAngle();
    }

    public double getAdsRecoilReduction(Gun gun)
    {
        return 1.0 - gun.getGeneral().getRecoilAdsReduction() * AimingHandler.get().getNormalisedAdsProgress();
    }

    public double getGunRecoilNormal()
    {
        return this.gunRecoilNormal;
    }

    public double getGunRecoilAngle()
    {
        return this.gunRecoilAngle;
    }

    public float getGunRecoilRandom()
    {
        return this.gunRecoilRandom;
    }
}
