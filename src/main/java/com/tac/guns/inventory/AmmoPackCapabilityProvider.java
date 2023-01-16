package com.tac.guns.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AmmoPackCapabilityProvider implements ICapabilitySerializable<ListTag> {

    public static Capability<IAmmoItemHandler> capability = CapabilityManager.get(new CapabilityToken<>(){});
    private IAmmoItemHandler itemHandler;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == capability ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    IAmmoItemHandler getOrCreateCapability() {
        if (itemHandler == null) {
            this.itemHandler = new AmmoItemStackHandler(18);
        }
        return this.itemHandler;
    }

    @Override
    public ListTag serializeNBT() {
        ListTag nbtTagList = new ListTag();
        int size = getOrCreateCapability().getSlots();
        for (int i = 0; i < size; i++) {
            ItemStack stack = getOrCreateCapability().getStackInSlot(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stack.save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        return nbtTagList;
    }

    @Override
    public void deserializeNBT(ListTag nbt) {
        if (!(getOrCreateCapability() instanceof IItemHandlerModifiable))
            throw new RuntimeException("IItemHandler instance does not implement IItemHandlerModifiable");
        IItemHandlerModifiable itemHandlerModifiable = (IItemHandlerModifiable) getOrCreateCapability();
        ListTag tagList = nbt;
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int j = itemTags.getInt("Slot");

            if (j >= 0 && j < getOrCreateCapability().getSlots()) {
                itemHandlerModifiable.setStackInSlot(j, ItemStack.of(itemTags));
            }
        }
    }
}
