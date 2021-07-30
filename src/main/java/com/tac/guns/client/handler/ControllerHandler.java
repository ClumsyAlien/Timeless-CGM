package com.tac.guns.client.handler;

import com.mrcrayfish.controllable.Controllable;
import com.mrcrayfish.controllable.client.Action;
import com.mrcrayfish.controllable.client.Controller;
import com.mrcrayfish.controllable.client.gui.navigation.BasicNavigationPoint;
import com.mrcrayfish.controllable.event.ControllerEvent;
import com.mrcrayfish.controllable.event.GatherActionsEvent;
import com.mrcrayfish.controllable.event.GatherNavigationPointsEvent;
import com.tac.guns.Config;
import com.tac.guns.client.GunButtonBindings;
import com.tac.guns.client.screen.WorkbenchScreen;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModSyncedDataKeys;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.attachment.IScope;
import com.tac.guns.item.attachment.impl.Scope;
import com.tac.guns.network.PacketHandler;
import com.tac.guns.network.message.MessageAttachments;
import com.tac.guns.network.message.MessageUnload;
import com.tac.guns.util.GunEnchantmentHelper;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Author: MrCrayfish
 */
public class ControllerHandler
{
    private int reloadCounter = -1;

    @SubscribeEvent
    public void onButtonInput(ControllerEvent.ButtonInput event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        World world = Minecraft.getInstance().level;
        if(player != null && world != null && Minecraft.getInstance().screen == null)
        {
            ItemStack heldItem = player.getMainHandItem();
            int button = event.getButton();
            if(button == GunButtonBindings.SHOOT.getButton())
            {
                if(heldItem.getItem() instanceof GunItem)
                {
                    event.setCanceled(true);
                    if(event.getState())
                    {
                        ShootingHandler.get().fire(player, heldItem);
                    }
                }
            }
            else if(button == GunButtonBindings.AIM.getButton())
            {
                if(heldItem.getItem() instanceof GunItem)
                {
                    event.setCanceled(true);
                }
            }
            else if(button == GunButtonBindings.STEADY_AIM.getButton())
            {
                if(heldItem.getItem() instanceof GunItem)
                {
                    event.setCanceled(true);
                }
            }
            else if(button == GunButtonBindings.RELOAD.getButton())
            {
                if(heldItem.getItem() instanceof GunItem)
                {
                    event.setCanceled(true);
                    if(event.getState())
                    {
                        this.reloadCounter = 0;
                    }
                }
            }
            else if(button == GunButtonBindings.OPEN_ATTACHMENTS.getButton())
            {
                if(heldItem.getItem() instanceof GunItem && Minecraft.getInstance().screen == null)
                {
                    event.setCanceled(true);
                    if(event.getState())
                    {
                        PacketHandler.getPlayChannel().sendToServer(new MessageAttachments());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onControllerTurn(ControllerEvent.Turn event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        if(player != null)
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem && AimingHandler.get().isAiming())
            {
                double adsSensitivity = Config.CLIENT.controls.aimDownSightSensitivity.get();
                event.setYawSpeed(10.0F * (float) adsSensitivity);
                event.setPitchSpeed(7.5F * (float) adsSensitivity);

                Scope scope = Gun.getScope(heldItem);
                if(scope != null && scope.isStable() && Controllable.isButtonPressed(GunButtonBindings.STEADY_AIM.getButton()))
                {
                    event.setYawSpeed(event.getYawSpeed() / 2.0F);
                    event.setPitchSpeed(event.getPitchSpeed() / 2.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public void updateAvailableActions(GatherActionsEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.screen != null) return;

        PlayerEntity player = Minecraft.getInstance().player;
        if(player != null)
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem)
            {
                event.getActions().put(GunButtonBindings.AIM, new Action("Aim", Action.Side.RIGHT));
                event.getActions().put(GunButtonBindings.SHOOT, new Action("Shoot", Action.Side.RIGHT));

                GunItem gunItem = (GunItem) heldItem.getItem();
                Gun modifiedGun = gunItem.getModifiedGun(heldItem);
                CompoundNBT tag = heldItem.getTag();
                if(tag != null && tag.getInt("AmmoCount") < GunEnchantmentHelper.getAmmoCapacity(heldItem, modifiedGun))
                {
                    event.getActions().put(GunButtonBindings.RELOAD, new Action("Reload", Action.Side.LEFT));
                }

                ItemStack scopeStack = Gun.getScopeStack(heldItem);
                if(scopeStack.getItem() instanceof IScope && AimingHandler.get().isAiming())
                {
                    IScope iscope = (IScope) scopeStack.getItem();
                    Scope scope = iscope.getProperties();
                    if(scope.isStable())
                    {
                        event.getActions().put(GunButtonBindings.STEADY_AIM, new Action("Hold Breath", Action.Side.RIGHT));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event)
    {
        Controller controller = Controllable.getController();
        if(controller == null)
            return;

        if(event.phase == TickEvent.Phase.END)
            return;

        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if(player == null)
            return;

        if(Controllable.isButtonPressed(GunButtonBindings.SHOOT.getButton()) && Minecraft.getInstance().screen == null)
        {
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() instanceof GunItem)
            {
                Gun gun = ((GunItem) heldItem.getItem()).getModifiedGun(heldItem);
                if(gun.getGeneral().isAuto())
                {
                    ShootingHandler.get().fire(player, heldItem);
                }
            }
        }

        if(mc.screen == null && this.reloadCounter != -1)
        {
            if(Controllable.isButtonPressed(GunButtonBindings.RELOAD.getButton()))
            {
                this.reloadCounter++;
            }
        }

        if(this.reloadCounter > 40)
        {
            ReloadHandler.get().setReloading(false);
            PacketHandler.getPlayChannel().sendToServer(new MessageUnload());
            this.reloadCounter = -1;
        }
        else if(this.reloadCounter > 0 && !Controllable.isButtonPressed(GunButtonBindings.RELOAD.getButton()))
        {
            ReloadHandler.get().setReloading(!SyncedPlayerData.instance().get(player, ModSyncedDataKeys.RELOADING));
            this.reloadCounter = -1;
        }
    }

    public static boolean isAiming()
    {
        Controller controller = Controllable.getController();
        return controller != null && Controllable.isButtonPressed(GunButtonBindings.AIM.getButton());
    }

    public static boolean isShooting()
    {
        Controller controller = Controllable.getController();
        return controller != null && Controllable.isButtonPressed(GunButtonBindings.SHOOT.getButton());
    }

    @SubscribeEvent
    public void onGatherNavigationPoints(GatherNavigationPointsEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.screen instanceof WorkbenchScreen)
        {
            WorkbenchScreen workbench = (WorkbenchScreen) mc.screen;
            int startX = workbench.getGuiLeft();
            int startY = workbench.getGuiTop();

            for(int i = 0; i < workbench.getTabs().size(); i++)
            {
                int tabX = startX + 28 * i + (28 / 2);
                int tabY = startY - (28 / 2);
                event.addPoint(new BasicNavigationPoint(tabX, tabY));
            }

            for(int i = 0; i < 6; i++)
            {
                int itemX = startX + 172 + (80 / 2);
                int itemY = startY + i * 19 + 63 + (19 / 2);
                event.addPoint(new BasicNavigationPoint(itemX, itemY));
            }
        }
    }
}
