package com.tac.guns.client.handler;

import com.tac.guns.Config;
import com.tac.guns.GunMod;
import com.tac.guns.common.Gun;
import com.tac.guns.event.GunFireEvent;
import com.tac.guns.item.GunItem;
import com.tac.guns.util.GunEnchantmentHelper;
import com.tac.guns.util.GunModifierHelper;
import com.tac.guns.util.math.SecondOrderDynamics;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.Random;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
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
    private int recoilRand;
    private double gunRecoilNormal;
    private double gunRecoilAngle;
    private double gunHorizontalRecoilAngle;
    private float gunRecoilRandom;

    public float cameraRecoil; // READONLY

    private float progressCameraRecoil;

    public float horizontalCameraRecoil; // READONLY

    private float horizontalProgressCameraRecoil;

    private int timer;

    private long prevTime = System.currentTimeMillis();

    private final int recoilDuration = 200; //0.20s

    private RecoilHandler() {}

    @SubscribeEvent
    public void preShoot(GunFireEvent.Pre event) {
        if(!(event.getStack().getItem() instanceof GunItem))
            return;
        this.recoilRand = this.random.nextInt(2);
    }

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

        float verticalRandomAmount = this.random.nextFloat()*(1.22f - 0.75f) + 0.75f;

        float recoilModifier = 1.0F - GunModifierHelper.getRecoilModifier(heldItem);
        recoilModifier *= this.getAdsRecoilReduction(modifiedGun);
        recoilModifier *= GunEnchantmentHelper.getBufferedRecoil(heldItem);
        recoilModifier *= verticalRandomAmount;
        this.cameraRecoil = modifiedGun.getGeneral().getRecoilAngle() * recoilModifier;
        this.progressCameraRecoil = 0F;

        // Horizontal Recoil
        this.gunRecoilRandom = random.nextFloat();

        float horizontalRandomAmount = this.random.nextFloat()*(1.22f - 0.75f) + 0.75f;

        float horizontalRecoilModifier = 1.0F - GunModifierHelper.getHorizontalRecoilModifier(heldItem);
        horizontalRecoilModifier *= this.getAdsRecoilReduction(modifiedGun);
        horizontalRecoilModifier *= GunEnchantmentHelper.getBufferedRecoil(heldItem);
        horizontalRecoilModifier *= horizontalRandomAmount;
        horizontalCameraRecoil = (modifiedGun.getGeneral().getHorizontalRecoilAngle() * horizontalRecoilModifier * 0.75F);
        horizontalProgressCameraRecoil = 0F;

        timer = recoilDuration;
    }
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        if(timer > 0) timer -= System.currentTimeMillis() - prevTime;
        prevTime = System.currentTimeMillis();
        if(timer < 0) timer = 0;

        if(!Config.SERVER.enableCameraRecoil.get())
            return;

        if(event.phase != TickEvent.Phase.END || this.cameraRecoil <= 0)
            return;

        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null)
            return;

        float cameraRecoilModifer = mc.player.getMainHandItem().getItem() instanceof GunItem ? ((GunItem) mc.player.getMainHandItem().getItem()).getGun().getGeneral().getCameraRecoilModifier() : 1.0F;

        float recoilAmount = this.cameraRecoil * mc.getDeltaFrameTime() * 0.2F;//0.25F;//0.1F;
        float HorizontalRecoilAmount = this.horizontalCameraRecoil * mc.getDeltaFrameTime() * 0.1F;//0.25F;//* 0.1F;
        float startProgress = (this.progressCameraRecoil / this.cameraRecoil);
        float endProgress = ((this.progressCameraRecoil + recoilAmount) / this.cameraRecoil);

        float progressForward = mc.player.getMainHandItem().getItem() instanceof GunItem ? ((GunItem) mc.player.getMainHandItem().getItem()).getGun().getGeneral().getRecoilDuration() : 0.25F;//0.25F;//startProgress < 0.25F && startProgress > 0.125F ? 0.125F : proggress; // 0.25

        if(startProgress < progressForward) // && startProgress > 0.125F
        {
            mc.player.setXRot(mc.player.getXRot() - ((endProgress - startProgress) / progressForward) * this.cameraRecoil / cameraRecoilModifer);
            if(recoilRand == 1)
                mc.player.setYRot(mc.player.getYRot() - ((endProgress - startProgress) / progressForward) * this.horizontalCameraRecoil / cameraRecoilModifer);
            else
                mc.player.setYRot(mc.player.getYRot() -  ((endProgress - startProgress) / progressForward) * -this.horizontalCameraRecoil / cameraRecoilModifer);
        }
        else if(startProgress > progressForward)
        {
            mc.player.setXRot((float) (mc.player.getXRot() + ((endProgress - startProgress) / (1-progressForward) ) * this.cameraRecoil / (cameraRecoilModifer*1.05))); // 0.75F
            if(recoilRand == 1)
                mc.player.setYRot((float) (mc.player.getYRot() - ((endProgress - startProgress) / (1-progressForward)) * -this.horizontalCameraRecoil / (cameraRecoilModifer*1.05)));
            else
                mc.player.setYRot((float) (mc.player.getYRot() - ((endProgress - startProgress) / (1-progressForward)) * this.horizontalCameraRecoil / (cameraRecoilModifer*1.05)));
        }

        this.progressCameraRecoil += recoilAmount;

        if(this.progressCameraRecoil >= this.cameraRecoil)
        {
            this.cameraRecoil = 0;
            this.progressCameraRecoil = 0;
        }

        this.horizontalProgressCameraRecoil += HorizontalRecoilAmount;

        if(this.horizontalProgressCameraRecoil >= this.horizontalCameraRecoil)
        {
            this.horizontalCameraRecoil = 0;
            this.horizontalProgressCameraRecoil = 0;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderOverlay(RenderHandEvent event)
    {
        if(event.getHand() != InteractionHand.MAIN_HAND)
            return;

        ItemStack heldItem = event.getItemStack();
        if(!(heldItem.getItem() instanceof GunItem))
            return;

        GunItem gunItem = (GunItem) heldItem.getItem();
        Gun modifiedGun = gunItem.getModifiedGun(heldItem);
        float cooldown = (float) timer / recoilDuration;
/*float cooldown ;
        if((tracker.getCooldown(gunItem, Minecraft.getInstance().getRenderPartialTicks()))<0.5f)
            cooldown = 0;/*(tracker.getCooldown(gunItem,
                    Minecraft.getInstance().getRenderPartialTicks()));
        else
        cooldown = (tracker.getCooldown(gunItem,
                Minecraft.getInstance().getRenderPartialTicks())-0.5f)*2f;*/
        if(cooldown >= modifiedGun.getGeneral().getWeaponRecoilOffset())// || tooFast) // Actually have any visual recoil at Rate 1???
        {
            float amount = 1F * ((1.0F - cooldown) / (1-modifiedGun.getGeneral().getWeaponRecoilOffset()));
            this.gunRecoilNormal = 1 - (--amount);
        }
        else {
            float amount = ( (cooldown) / modifiedGun.getGeneral().getWeaponRecoilOffset() );
            this.gunRecoilNormal = amount < 0.5 ? 2 * amount * amount : -1 + (4 - 2 * amount) * amount;
        }

        this.gunRecoilAngle = modifiedGun.getGeneral().getRecoilAngle();
        this.gunHorizontalRecoilAngle = modifiedGun.getGeneral().getHorizontalRecoilAngle();
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

    public double getGunHorizontalRecoilAngle()
    {
        return this.gunHorizontalRecoilAngle;
    }

    public float getGunRecoilRandom()
    {
        return this.gunRecoilRandom;
    }

    public double getRecoilProgress() {return timer / (double)recoilDuration;}
}
