package com.tac.guns.extra_events;
/**
 * This class will be used for all shooting events that I will utilise.
 * The gun mod provides 3 events for firing guns check out {@link com.mrcrayfish.guns.event.GunFireEvent} for what they are
 */

/*
import com.tac.guns.Config;
import com.tac.guns.GunMod;
import com.tac.guns.Reference;
import com.tac.guns.common.Gun;
import com.tac.guns.event.GunFireEvent;
import com.tac.guns.init.ModSounds;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.Array;
import java.util.*;

*/
/**
 * Author: ClumsyAlien
 *//*

// @Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TacShootingEvent {
    */
/**
     * @param event the event. In this case, the Pre shoot event {@link GunFireEvent.Pre} which is fired when a player is about to shoot a bullet
     *//*


    */
/*
        A bit decent bit of extra code will be locked in external methods such as this, separating some of the standard and advanced
        Functions, especially in order to keep it all clean and allow easy backtracking, however both functions may receive changes
        For now as much of the work I can do will be kept externally such as with fire selection, and burst fire.
        (In short this serves as a temporary test bed to keep development on new functions on course)
    *//*


    // @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event)
    {
        // Our gun?
        if(!(event.getStack().getItem() instanceof TimelessGunItem))
            return;

        if(Config.COMMON.gameplay.fireModeSelection.get())
        {
            Handle_FireMode(event);
        }

        //Check if the gun is jammed from the NBT data
        //if(event.getStack().getTag().getBoolean("isJammed")) {
            //Inform the player
        //    if(Config.COMMON.showJamStatusMessage.get()) {
        //        event.getPlayer().sendStatusMessage(new TranslationTextComponent("info." + ToyGuns.ID + ".gun_jammed", (new KeybindTextComponent("key." + ToyGuns.ID + ".unjam_gun")).getString().toUpperCase(Locale.ENGLISH)).mergeStyle(TextFormatting.RED), true);
        //    }
            //Cancel the event, ultimately, not firing the gun
        //    event.setCanceled(true);
        //}
    }

    */
/*

    *//*


    private static void Handle_FireMode(GunFireEvent.Pre event)
    {
        */
/*ItemStack gunItem = event.getStack();
        int[] gunItemFireModes = gunItem.getTag().getIntArray("supportedFireModes");

        // Check if the weapon is new, add in all supported modes
        if(gunItemFireModes == null)
        {
            Gun gun = ((TimelessGunItem) event.getStack().getItem()).getModifiedGun(event.getStack());
            gunItemFireModes = gun.getGeneral().getRateSelector();
            gunItem.getTag().putIntArray("supportedFireModes",gunItemFireModes);
        }
        int currentFireMode = 0;
        if(gunItem.getTag().get("CurrentFireMode") == null)
            gunItem.getTag().putInt("CurrentFireMode",0);
        else
            currentFireMode = gunItem.getTag().getInt("CurrentFireMode");

        switch(currentFireMode)
        {
            case 0:
            {
                if(Config.COMMON.gameplay.fireModeSelection.get())
                {
                    //event.getPlayer().sendMessage(new TranslationTextComponent("info." + Reference.MOD_ID + ".gun_safety_lock", new Object[]{(new KeybindTextComponent("key." + Reference.MOD_ID + ".fire_select")).getString().toUpperCase(Locale.ENGLISH)})).withStyle(TextFormatting.RED));
                    event.getPlayer().displayClientMessage(new TranslationTextComponent("info." + Reference.MOD_ID + ".gun_safety_lock"),true);
                }
                event.setCanceled(true);
            }
            case 1:
            {
                if (gunItem.getTag().getInt("BulletCounter") > 0)
                    event.setCanceled(true);
            }
            case 2: {}
            case 3:
            {
                event.setCanceled(true);
            }
            case 4:
            {
                event.setCanceled(true);
            }
        }*//*

    }
}*/
