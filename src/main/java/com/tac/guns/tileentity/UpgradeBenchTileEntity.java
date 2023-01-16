package com.tac.guns.tileentity;

import com.tac.guns.common.container.UpgradeBenchContainer;
import com.tac.guns.init.ModTileEntities;
import com.tac.guns.tileentity.inventory.IStorageBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class UpgradeBenchTileEntity extends SyncedTileEntity implements IStorageBlock
{
    private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

    public UpgradeBenchTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.WORKBENCH.get(), pos, state);
    }


    @Override
    public NonNullList<ItemStack> getInventory()
    {
        return this.inventory;
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag nbtTagCompound = new CompoundTag();
        this.saveAdditional(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());   // read from the nbt in the packet
    }
    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        CompoundTag nbtTagCompound = new CompoundTag();
        this.saveAdditional(nbtTagCompound);
        int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.
        return ClientboundBlockEntityDataPacket.create(this, blockEntity -> nbtTagCompound);
    }

    @Override
    public void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);

        CompoundTag weaponBt = new CompoundTag();
        this.inventory.get(0).save(weaponBt);
        if(this.inventory.get(0).getTag() != null)
            compound.put("weapon", weaponBt);

        CompoundTag modules = new CompoundTag();
        this.inventory.get(1).save(modules);
        if(this.inventory.get(1).getOrCreateTag() != null)
            compound.put("modules", modules);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        if(compound.contains("weapon"))
            this.inventory.set(0, ItemStack.of(compound.getCompound("weapon")));

        CompoundTag itemStackNBT = compound.getCompound("modules");
        ItemStack readItemStack = ItemStack.of(itemStackNBT);
        this.inventory.set(1, readItemStack);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent("container.tac.upgradeBench");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity)
    {
        return new UpgradeBenchContainer(windowId, playerInventory, this);
    }
}
