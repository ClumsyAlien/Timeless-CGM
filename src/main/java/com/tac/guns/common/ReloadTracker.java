package com.tac.guns.common;

import com.tac.guns.Reference;
import com.tac.guns.init.ModSyncedDataKeys;
import com.tac.guns.item.GunItem;
import com.tac.guns.network.PacketHandler;
import com.tac.guns.network.message.MessageGunSound;
import com.tac.guns.util.GunEnchantmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ReloadTracker
{
    private static final Map<Player, ReloadTracker> RELOAD_TRACKER_MAP = new WeakHashMap<>();

    private final int startTick;
    private final int slot;
    private final ItemStack stack;
    private final Gun gun;

    private ReloadTracker(Player player)
    {
        this.startTick = player.tickCount;
        this.slot = player.getInventory().selected;
        this.stack = player.getInventory().getSelected();
        this.gun = ((GunItem) stack.getItem()).getModifiedGun(stack);
    }

    /**
     * Tests if the current item the player is holding is the same as the one being reloaded
     *
     * @param player the player to check
     * @return True if it's the same weapon and slot
     */
    private boolean isSameWeapon(Player player)
    {
        return !this.stack.isEmpty() && player.getInventory().selected == this.slot && player.getInventory().getSelected() == this.stack;
    }

    /**
     * @return
     */
    private boolean isWeaponFull()
    {
        CompoundTag tag = this.stack.getOrCreateTag();
        return tag.getInt("AmmoCount") >= GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun);
    }
    /**
     * @return
     */
    private boolean isWeaponEmpty()
    {
        CompoundTag tag = this.stack.getOrCreateTag();
        return tag.getInt("AmmoCount") == 0;
    }

    private boolean hasNoAmmo(Player player)
    {
        return Gun.findAmmo(player, this.gun.getProjectile().getItem()).length == 0 ? true : Gun.findAmmo(player, this.gun.getProjectile().getItem())[0].isEmpty();
    }

    private boolean canReload(Player player)
    {
        boolean reload;
        Gun gun = ((GunItem)this.stack.getItem()).getGun();
        if(gun.getReloads().isMagFed())
        {
            if(this.isWeaponEmpty())
            {
                int deltaTicks = player.tickCount - this.startTick;
                int interval = gun.getReloads().getReloadMagTimer()+gun.getReloads().getAdditionalReloadEmptyMagTimer()+this.gun.getReloads().getPreReloadPauseTicks();//GunEnchantmentHelper.getReloadInterval(this.stack);
                reload = deltaTicks > interval; // deltaTicks > 0 &&
            }
            else
            {
                int deltaTicks = player.tickCount - this.startTick;
                int interval = gun.getReloads().getReloadMagTimer()+this.gun.getReloads().getPreReloadPauseTicks();//GunEnchantmentHelper.getReloadInterval(this.stack);
                reload = deltaTicks > interval; // deltaTicks > 0 &&
            }
        }
        else
        {
            int deltaTicks = player.tickCount - this.startTick;
            int interval = GunEnchantmentHelper.getReloadInterval(this.stack);
            reload = deltaTicks > 0 && deltaTicks % interval == 0;
        }
        return reload;
    }

    private void increaseAmmo(Player player)
    {
        ItemStack ammo = Gun.findAmmo(player, this.gun.getProjectile().getItem())[0];
        if(!ammo.isEmpty())
        {
            CompoundTag tag = this.stack.getTag();
            int amount = Math.min(ammo.getCount(), this.gun.getReloads().getReloadAmount());
            if (tag != null) {
                int maxAmmo = GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun);
                amount = Math.min(amount, maxAmmo - tag.getInt("AmmoCount"));
                tag.putInt("AmmoCount", tag.getInt("AmmoCount") + amount);
            }
            ammo.shrink(amount);
        }

        ResourceLocation reloadSound = this.gun.getSounds().getReload();
        if(reloadSound != null)
        {
            MessageGunSound message = new MessageGunSound(reloadSound, SoundSource.PLAYERS, (float) player.getX(), (float) player.getY() + 1.0F, (float) player.getZ(), 1.0F, 1.0F, player.getId(), false, true);
            PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), (player.getY() + 1.0), player.getZ(), 16.0, player.level.dimension())), message);
        }
    }

    public static int calcMaxReserveAmmo(ItemStack[] ammoStacks)
    {
        int result = 0;
        for (ItemStack x: ammoStacks)
            result+=x.getCount();
        return result;
    }

    private void shrinkFromAmmoPool(ItemStack[] ammoStacks, int shrinkAmount)
    {
        int shrinkAmt = shrinkAmount;
        for (ItemStack x: ammoStacks)
        {
            if(!x.isEmpty())
            {
                int max = shrinkAmt > x.getCount() ? x.getCount() : shrinkAmt;
                x.shrink(max);
                shrinkAmt-=max;
            }
            if(shrinkAmt==0)
                return;
        }
    }

    private void increaseMagAmmo(Player player)
    {
        ItemStack[] ammoStacks = Gun.findAmmo(player, this.gun.getProjectile().getItem());
        int stackItor = 0;
        //ItemStack ammo = Gun.findAmmo(player, this.gun.getProjectile().getItem());
        if(ammoStacks.length > 0)
        {
            CompoundTag tag = this.stack.getTag();
            int ammoAmount = Math.min(calcMaxReserveAmmo(ammoStacks), GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun));
            if (tag != null) {
                int currentAmmo = tag.getInt("AmmoCount");
                int maxAmmo = GunEnchantmentHelper.getAmmoCapacity(this.stack, this.gun);
                int amount = maxAmmo - currentAmmo; //amount < maxAmmo ? maxAmmo - amount :
                if(currentAmmo == 0 && !this.gun.getReloads().isOpenBolt())
                {
                    if(ammoAmount < amount) {
                        tag.putInt("AmmoCount", currentAmmo+ammoAmount);
                        this.shrinkFromAmmoPool(ammoStacks, ammoAmount);
                    }
                    else {
                        tag.putInt("AmmoCount", maxAmmo - 1);
                        this.shrinkFromAmmoPool(ammoStacks, amount-1);
                    }
                }
                else {
                    if(ammoAmount < amount) {
                        tag.putInt("AmmoCount", currentAmmo+ammoAmount);
                        this.shrinkFromAmmoPool(ammoStacks, ammoAmount);
                    }
                    else {
                        tag.putInt("AmmoCount", maxAmmo);
                        this.shrinkFromAmmoPool(ammoStacks, amount);//ammoStacks.shrink(amount);
                    }
                }
            }
        }

        ResourceLocation reloadSound = this.gun.getSounds().getReload();
        if(reloadSound != null)
        {
            MessageGunSound message = new MessageGunSound(reloadSound, SoundSource.PLAYERS, (float) player.getX(), (float) player.getY() + 1.0F, (float) player.getZ(), 1.0F, 1.0F, player.getId(), false, true);
            PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), (player.getY() + 1.0), player.getZ(), 16.0, player.level.dimension())), message);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START && !event.player.level.isClientSide)
        {
            Player player = event.player;
            if(ModSyncedDataKeys.RELOADING.getValue(player))
            {
                if(!RELOAD_TRACKER_MAP.containsKey(player))
                {
                    if(!(player.getInventory().getSelected().getItem() instanceof GunItem))
                    {
                        ModSyncedDataKeys.RELOADING.setValue(player, false);
                        ModSyncedDataKeys.STOP_ANIMA.setValue(player, false);
                        return;
                    }
                    RELOAD_TRACKER_MAP.put(player, new ReloadTracker(player));
                }
                ReloadTracker tracker = RELOAD_TRACKER_MAP.get(player);
                if(!tracker.isSameWeapon(player) || tracker.isWeaponFull() || tracker.hasNoAmmo(player))
                {
                    RELOAD_TRACKER_MAP.remove(player);
                    ModSyncedDataKeys.RELOADING.setValue(player, false);
                    ModSyncedDataKeys.STOP_ANIMA.setValue(player, true);
                    return;
                }
                if(tracker.canReload(player))
                {
                    final Player finalPlayer = player;
                    final Gun gun = tracker.gun;
                    if(gun.getReloads().isMagFed())
                    {
                        tracker.increaseMagAmmo(player);
                        RELOAD_TRACKER_MAP.remove(player);
                        ModSyncedDataKeys.RELOADING.setValue(player, false);
                        ModSyncedDataKeys.STOP_ANIMA.setValue(player, false);
                        /*DelayedTask.runAfter(2, () ->
                        {
                            ResourceLocation cockSound = gun.getSounds().getCock();
                            if (cockSound != null && finalPlayer.isAlive()) {
                                MessageGunSound messageSound = new MessageGunSound(cockSound, SoundCategory.PLAYERS, (float) finalPlayer.getPosX(), (float) (finalPlayer.getPosY() + 1.0), (float) finalPlayer.getPosZ(), 1.0F, 1.0F, finalPlayer.getEntityId(), false, true);
                                PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(finalPlayer.getPosX(), (finalPlayer.getPosY() + 1.0), finalPlayer.getPosZ(), 16.0, finalPlayer.world.getDimensionKey())), messageSound);
                            }
                        });*/
                    }
                    else {
                        tracker.increaseAmmo(player);
                        if (tracker.isWeaponFull() || tracker.hasNoAmmo(player)) {
                            RELOAD_TRACKER_MAP.remove(player);
                            ModSyncedDataKeys.RELOADING.setValue(player, false);
                            ModSyncedDataKeys.STOP_ANIMA.setValue(player, false);
                            /*DelayedTask.runAfter(4, () ->
                            {
                                ResourceLocation cockSound = gun.getSounds().getCock();
                                if (cockSound != null && finalPlayer.isAlive()) {
                                    MessageGunSound messageSound = new MessageGunSound(cockSound, SoundCategory.PLAYERS, (float) finalPlayer.getPosX(), (float) (finalPlayer.getPosY() + 1.0), (float) finalPlayer.getPosZ(), 1.0F, 1.0F, finalPlayer.getEntityId(), false, true);
                                    PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(finalPlayer.getPosX(), (finalPlayer.getPosY() + 1.0), finalPlayer.getPosZ(), 16.0, finalPlayer.world.getDimensionKey())), messageSound);
                                }
                            });*/
                        }
                    }
                }
            }
            else if(RELOAD_TRACKER_MAP.containsKey(player))
            {
                RELOAD_TRACKER_MAP.remove(player);
            }
        }
    }

    public static boolean isPlayerReload(Player player){
        return RELOAD_TRACKER_MAP.containsKey(player);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerEvent.PlayerLoggedOutEvent event)
    {
        MinecraftServer server = event.getPlayer().getServer();
        if(server != null)
        {
            server.execute(() -> RELOAD_TRACKER_MAP.remove(event.getPlayer()));
        }
    }
}
