package com.tac.guns.common.container;

import com.tac.guns.client.handler.command.GuiEditor;
import com.tac.guns.crafting.WorkbenchRecipes;
import com.tac.guns.init.ModContainers;
import com.tac.guns.item.GunItem;
import com.tac.guns.tileentity.UpgradeBenchTileEntity;
import com.tac.guns.tileentity.WorkbenchTileEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class UpgradeBenchContainer extends AbstractContainerMenu
{
    private UpgradeBenchTileEntity upgradeBench;
    private BlockPos pos;

    public UpgradeBenchContainer(int windowId, Container playerInventory, UpgradeBenchTileEntity workbench)
    {
        super(ModContainers.UPGRADE_BENCH.get(), windowId);
        this.upgradeBench = workbench;
        this.pos = workbench.getBlockPos();

        int offset = WorkbenchRecipes.isEmpty(workbench.getLevel()) ? 0 : 28;

        this.addSlot(new Slot(workbench, 0, 17400, 40000)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return true;
            }

            @Override
            public int getMaxStackSize()
            {
                return 1;
            }
        });
        this.addSlot(new Slot(workbench, 1, 17600, 50000)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return true;
            }

            @Override
            public int getMaxStackSize()
            {
                return 10;
            }
        });

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, -112 + 8 + x * 18, 103 + y * 18)); // 102
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInventory, x,  -112 + 8 + x * 18, 161)); // 160
        }

    }
    @Override
    public boolean stillValid(Player playerIn)
    {
        return upgradeBench.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem())
        {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();

            if(index == 0)
            {
                if(!this.moveItemStackTo(slotStack, 1, 36, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if(slotStack.getItem() instanceof DyeItem)
                {
                    if(!this.moveItemStackTo(slotStack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(index < 28)
                {
                    if(!this.moveItemStackTo(slotStack, 28, 36, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if(index <= 36 && !this.moveItemStackTo(slotStack, 1, 28, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if(slotStack.isEmpty())
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if(slotStack.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return stack;
    }

    public UpgradeBenchTileEntity getBench()
    {
        return upgradeBench;
    }

    public BlockPos getPos()
    {
        return pos;
    }

}
