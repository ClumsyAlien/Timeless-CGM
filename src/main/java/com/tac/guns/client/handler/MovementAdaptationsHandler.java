package com.tac.guns.client.handler;

import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.network.PacketHandler;
import com.tac.guns.network.message.MessageUpdatePlayerMovement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MovementAdaptationsHandler
{
    private static MovementAdaptationsHandler instance;

    public boolean isReadyToUpdate() {
        return readyToUpdate;
    }

    public void setReadyToUpdate(boolean readyToUpdate) {
        this.readyToUpdate = readyToUpdate;
    }

    public boolean isReadyToReset() {
        return readyToReset;
    }

    public void setReadyToReset(boolean readyToReset) {
        this.readyToReset = readyToReset;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(float previousWeight) {
        this.previousWeight = previousWeight;
    }

    private boolean readyToUpdate = false;
    private boolean readyToReset = true;

    private float speed = 0.0F;
    private float previousWeight = 0.0F;

    public float getMovement() {
        return movement;
    }

    private float movement = 0.0F;

    //private Byte previousGun;

    public static MovementAdaptationsHandler get() {
        return instance == null ? instance = new MovementAdaptationsHandler() : instance;
    }
    private MovementAdaptationsHandler() { }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onFovUpdate(FOVModifierEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null && !mc.player.getMainHandItem().isEmpty() && mc.options.getCameraType() == CameraType.FIRST_PERSON && mc.options.fovEffectScale > 0)
        {
            ItemStack heldItem = mc.player.getMainHandItem();
            if(heldItem.getItem() instanceof TimelessGunItem)
            {
                if(event.getEntity().isSprinting())
                    event.setNewfov(1.125f);
                else
                    event.setNewfov(1f);
            }
        }
    }
    //private float revisionFov(float speed)
    //{
    //    return speed > 0.1 ? speed : 1f;
    //}

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onJump(LivingEvent.LivingJumpEvent event)
    {
        if (!(event.getEntityLiving().getMainHandItem().getItem() instanceof TimelessGunItem))
            return;
        if(speed < 0.077f)
            event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().x()/2.5,event.getEntityLiving().getDeltaMovement().y()/1.125,event.getEntityLiving().getDeltaMovement().z()/2.5);
        else if(speed < 0.9f)
            event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().x()/1.75,event.getEntityLiving().getDeltaMovement().y(),event.getEntityLiving().getDeltaMovement().z()/1.75);
    }

    @SubscribeEvent//(priority = EventPriority.HIGH)
    public void movementUpdate(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getInstance().player == null) {
            return;
        }
        /*float dist =
                (Math.abs(Minecraft.getInstance().player.moveForward)/4+
                        Math.abs(Minecraft.getInstance().player.moveStrafing)/1.5f)*
                        (Minecraft.getInstance().player.moveVertical != 0 ? 3:1);
        if(dist != 0)
            SyncedPlayerData.instance().set(Minecraft.getInstance().player, ModSyncedDataKeys.MOVING, dist);
        else
            SyncedPlayerData.instance().set(Minecraft.getInstance().player, ModSyncedDataKeys.MOVING, 0f);*/
        PacketHandler.getPlayChannel().sendToServer(new MessageUpdatePlayerMovement());
    }
    /*@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void movementRec(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getInstance().player == null || !Config.COMMON.projectileSpread.movementInaccuracy.get()) {
            return;
        }
        PacketHandler.getPlayChannel().sendToServer(new MessageUpdatePlayerMovement(true));
    }*/
}