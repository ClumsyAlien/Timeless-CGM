package com.tac.guns.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class InventoryUtil
{
    public static int getItemStackAmount(PlayerEntity player, ItemStack find)
    {
        int count = 0;
        for(ItemStack stack : player.inventory.items)
        {
            if(!stack.isEmpty() && areItemStacksEqualIgnoreCount(stack, find))
            {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static boolean hasItemStack(PlayerEntity player, ItemStack find)
    {
        int count = 0;
        for(ItemStack stack : player.inventory.items)
        {
            if(!stack.isEmpty() && areItemStacksEqualIgnoreCount(stack, find))
            {
                count += stack.getCount();
            }
        }
        return find.getCount() <= count;
    }

    public static boolean removeItemStack(PlayerEntity player, ItemStack find)
    {
        int amount = find.getCount();
        for(int i = 0; i < player.inventory.getContainerSize(); i++)
        {
            ItemStack stack = player.inventory.getItem(i);
            if(!stack.isEmpty() && areItemStacksEqualIgnoreCount(stack, find))
            {
                if(amount - stack.getCount() < 0)
                {
                    stack.shrink(amount);
                    return true;
                }
                else
                {
                    amount -= stack.getCount();
                    player.inventory.items.set(i, ItemStack.EMPTY);
                    if(amount == 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean areItemStacksEqualIgnoreCount(ItemStack source, ItemStack target)
    {
        if(source.getItem() != target.getItem())
        {
            return false;
        }
        else if(source.getDamageValue() != target.getDamageValue())
        {
            return false;
        }
        else if(source.getTag() == null && target.getTag() != null)
        {
            return false;
        }
        else
        {
            return (source.getTag() == null || source.getTag().equals(target.getTag())) && source.areCapsCompatible(target);
        }
    }
}
