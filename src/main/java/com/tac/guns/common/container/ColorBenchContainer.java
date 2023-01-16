package com.tac.guns.common.container;

import com.tac.guns.common.Gun;
import com.tac.guns.common.container.slot.AttachmentSlot;
import com.tac.guns.common.container.slot.WeaponColorSegmentSlot;
import com.tac.guns.init.ModContainers;
import com.tac.guns.item.GunItem;
import com.tac.guns.item.IWeaponColorable;
import com.tac.guns.item.ScopeItem;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ColorBenchContainer extends AbstractContainerMenu
{
    private ItemStack weapon;
    private Container playerInventory;
    private Container weaponInventory = new SimpleContainer(IWeaponColorable.WeaponColorSegment.values().length)
    {
        @Override
        public void setChanged()
        {
            super.setChanged();
            ColorBenchContainer.this.slotsChanged(this);
        }
    };
    public ColorBenchContainer(int windowId, Inventory playerInventory)
    {
        super(ModContainers.COLOR_BENCH.get(), windowId);
        this.weapon = playerInventory.getSelected();
        this.playerInventory = playerInventory;

        if(this.weapon.getItem() instanceof GunItem)
        {
            for (int i = 0; i < IWeaponColorable.WeaponColorSegment.values().length; i++)
            {
                this.addSlot(new WeaponColorSegmentSlot(this, this.weaponInventory, this.weapon, playerInventory.player, i, (i * 18)-38, 6));//14- i * 18, 12));
            }
        }

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 102 + i * 18));
            }
        }

        for(int i = 0; i < 9; i++)
        {
            if(i == playerInventory.selected)
            {
                this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 160)
                {
                    @Override
                    public boolean mayPickup(Player playerIn)
                    {
                        return false;
                    }
                });
            }
            else
            {
                this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 160));
            }
        }
    }

    @Override
    public boolean stillValid(Player playerIn)
    {
        return true;
    }

/*    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        CompoundNBT attachments = new CompoundNBT();

        *//*if(this.weapon.getItem() instanceof ScopeItem)
        {
            for (int i = 0; i < this.getWeaponInventory().getSizeInventory()-4; i++) {
                ItemStack attachment = this.getSlot(i).getStack();
                if (attachment.getItem() instanceof DyeItem) {
                    attachments.put(currentStuff[i], attachment.write(new CompoundNBT()));
                }
            }

            *//**//*if (scopeReticleAttachment.getItem() instanceof DyeItem) {
                attachments.put(IAttachment.Type.SCOPE_RETICLE_COLOR.getTagKey(), scopeReticleAttachment.write(new CompoundNBT()));
            }
            if (scopeBodyAttachment.getItem() instanceof DyeItem) {
                attachments.put(IAttachment.Type.SCOPE_BODY_COLOR.getTagKey(), scopeBodyAttachment.write(new CompoundNBT()));
            }
            if (scopeGlassAttachment.getItem() instanceof DyeItem) {
                attachments.put(IAttachment.Type.SCOPE_GLASS_COLOR.getTagKey(), scopeGlassAttachment.write(new CompoundNBT()));
            }*//**//*
        }
        else*//*
            for (int i = 0; i < this.getWeaponInventory().getSizeInventory(); i++) {
                ItemStack attachment = this.getSlot(i).getStack();
                if (attachment.getItem() instanceof IAttachment) {
                    attachments.put(((IAttachment) attachment.getItem()).getType().getTagKey(), attachment.write(new CompoundNBT()));
                }
                else if(attachment.getItem() instanceof DyeItem)
                {
                    if(i == 0)
                        attachments.put(IAttachment.Type.SCOPE_RETICLE_COLOR.getTagKey(), attachment.write(new CompoundNBT()));
                    if(i == 1)
                        attachments.put(IAttachment.Type.SCOPE_BODY_COLOR.getTagKey(), attachment.write(new CompoundNBT()));
                    if(i == 2)
                        attachments.put(IAttachment.Type.SCOPE_GLASS_COLOR.getTagKey(), attachment.write(new CompoundNBT()));
                }
            }

        CompoundNBT tag = this.weapon.getOrCreateTag();
        tag.put("Attachments", attachments);
        super.detectAndSendChanges();
    }*/

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        ItemStack copyStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

/*        if (this.weapon.getItem() instanceof ScopeItem)
        {
            if (slot != null && slot.getHasStack()) {
                ItemStack slotStack = slot.getStack();
                copyStack = slotStack.copy();

                if (index == 0) {
                    if (!this.mergeItemStack(slotStack, 0, 36, true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (slotStack.getItem() instanceof DyeItem) {
                        if (!this.mergeItemStack(slotStack, 0, 3, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 28) {
                        if (!this.mergeItemStack(slotStack, 28, 36, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index <= 36 && !this.mergeItemStack(slotStack, 0, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                if (slotStack.isEmpty()) {
                    slot.putStack(ItemStack.EMPTY);
                } else {
                    slot.onSlotChanged();
                }

                if (slotStack.getCount() == copyStack.getCount()) {
                    return ItemStack.EMPTY;
                }

                slot.onTake(playerIn, slotStack);
            }
        }
        else {*/
            if (slot != null && slot.hasItem()) {
                ItemStack slotStack = slot.getItem();
                copyStack = slotStack.copy();
                if (index < this.weaponInventory.getContainerSize()) {
                    if (!this.moveItemStackTo(slotStack, this.weaponInventory.getContainerSize(), this.slots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(slotStack, 0, this.weaponInventory.getContainerSize(), false)) {
                    return ItemStack.EMPTY;
                }
                if (slotStack.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }
            }//}

        return copyStack;
    }

    public Container getPlayerInventory()
    {
        return this.playerInventory;
    }
}
