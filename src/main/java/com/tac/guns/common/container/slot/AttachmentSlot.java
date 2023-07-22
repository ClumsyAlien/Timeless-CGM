package com.tac.guns.common.container.slot;

import com.mrcrayfish.framework.common.data.SyncedEntityData;
import com.tac.guns.client.handler.ReloadHandler;
import com.tac.guns.common.Gun;
import com.tac.guns.common.container.AttachmentContainer;
import com.tac.guns.init.ModSounds;
import com.tac.guns.init.ModSyncedDataKeys;
import com.tac.guns.item.*;
import com.tac.guns.item.TransitionalTypes.TimelessGunItem;
import com.tac.guns.item.TransitionalTypes.TimelessPistolGunItem;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class AttachmentSlot extends Slot
{
    private AttachmentContainer container;
    private ItemStack weapon;
    private IAttachment.Type type;
    private Player player;
    private IAttachment.Type[] types;

    public AttachmentSlot(AttachmentContainer container, Container weaponInventory, ItemStack weapon, IAttachment.Type type, Player player, int index, int x, int y)
    {
        super(weaponInventory, index, x, y);
        this.container = container;
        this.weapon = weapon;
        this.type = type;
        this.player = player;
    }

    public AttachmentSlot(AttachmentContainer container, Container weaponInventory, ItemStack weapon, IAttachment.Type[] types, Player player, int index, int x, int y)
    {
        super(weaponInventory, index, x, y);
        this.container = container;
        this.weapon = weapon;
        this.types = types;
        this.player = player;
    }

    @Override
    public boolean isActive()
    {
        this.weapon.inventoryTick(player.level, player, index, true);
        if((this.type == IAttachment.Type.EXTENDED_MAG && this.weapon.getOrCreateTag().getInt("AmmoCount") > ((TimelessGunItem)this.weapon.getItem()).getGun().getReloads().getMaxAmmo()) || SyncedEntityData.instance().get(player,
                ModSyncedDataKeys.RELOADING) || EnchantmentHelper.hasBindingCurse(this.container.getWeaponInventory().getItem(this.index))) {
            return false;
        }
        if((this.player.getMainHandItem().getItem() instanceof ScopeItem || this.player.getMainHandItem().getItem() instanceof SideRailItem || this.player.getMainHandItem().getItem() instanceof IrDeviceItem))
        {
            return true;
        }
        else if (this.weapon.getItem() instanceof GunItem)
        {
            GunItem item = (GunItem) this.weapon.getItem();
            Gun modifiedGun = item.getModifiedGun(this.weapon);
            if(modifiedGun.canAttachType(this.type))
                return true;
            else if(types != null)
            {
                for (IAttachment.Type x : types) {
                    if(modifiedGun.canAttachType(x))
                        return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        if(stack.getItem() instanceof GunItem)
            return false;
        if((this.type == IAttachment.Type.EXTENDED_MAG && this.weapon.getOrCreateTag().getInt("AmmoCount") > ((TimelessGunItem)this.weapon.getItem()).getGun().getReloads().getMaxAmmo()) || SyncedEntityData.instance().get(player,
                ModSyncedDataKeys.RELOADING)) {
            return false;
        }
        if(this.player.getMainHandItem().getItem() instanceof ScopeItem || this.player.getMainHandItem().getItem() instanceof SideRailItem || this.player.getMainHandItem().getItem() instanceof IrDeviceItem)
            if(stack.getItem() instanceof DyeItem)
                return true;
            else
                return false;
        else
        {
            GunItem item = (GunItem) this.weapon.getItem();
            Gun modifiedGun = item.getModifiedGun(this.weapon);
            if(stack.getItem() instanceof IAttachment) {
                if (!modifiedGun.canAttachType(((IAttachment) stack.getItem()).getType()))
                    return false;
                if (((IAttachment) stack.getItem()).getType() == this.type && modifiedGun.canAttachType(this.type))
                    return true;
                else if (this.weapon.getItem() instanceof TimelessPistolGunItem && this.type == IAttachment.Type.PISTOL_SCOPE) {
                    return stack.getItem() instanceof PistolScopeItem;
                }
                else if (types != null && stack.getItem() instanceof IAttachment) {
                    for (IAttachment.Type x : types) {
                        if (((IAttachment) stack.getItem()).getType() == x)
                            return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void setChanged()
    {
        if(this.container.isLoaded())
        {
            this.player.level.playSound(null, this.player.getX(), this.player.getY() + 1.0, this.player.getZ(), ModSounds.UI_WEAPON_ATTACH.get(), SoundSource.PLAYERS, 0.5F, this.hasItem() ? 1.0F : 0.75F);
        }
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public boolean mayPickup(Player player)
    {
        return true;
    }
}
