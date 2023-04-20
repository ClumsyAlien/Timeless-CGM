package com.tac.guns.inventory.gear.armor;

import com.tac.guns.inventory.gear.GearSlotsHandler;
import com.tac.guns.inventory.gear.InventoryListener;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArmorRigCapabilityProvider implements ICapabilitySerializable<ListNBT> {

    @CapabilityInject(IAmmoItemHandler.class)
    public static Capability<IAmmoItemHandler> capability = InventoryListener.RIG_HANDLER_CAPABILITY;
    private IAmmoItemHandler itemHandler = new RigSlotsHandler(90);
    private LazyOptional<IAmmoItemHandler> optionalStorage = LazyOptional.of(() -> itemHandler);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == capability) {
            return optionalStorage.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public ListNBT serializeNBT() {
        return (ListNBT) capability.getStorage().writeNBT(capability, itemHandler, null);
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        capability.getStorage().readNBT(capability, itemHandler, null, nbt);
    }
}
