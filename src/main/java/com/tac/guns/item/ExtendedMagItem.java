package com.tac.guns.item;

import com.tac.guns.item.attachment.IExtendedMag;
import com.tac.guns.item.attachment.impl.ExtendedMag;
import com.tac.guns.item.attachment.impl.SideRail;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A basic under barrel attachment item implementation with color support
 *
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ExtendedMagItem extends Item implements IExtendedMag, IColored
{
    private final ExtendedMag extendedMag;
    private final boolean colored;

    public ExtendedMagItem(ExtendedMag extendedMag, Properties properties)
    {
        super(properties);
        this.extendedMag = extendedMag;
        this.colored = true;
    }

    public ExtendedMagItem(ExtendedMag extendedMag, Properties properties, boolean colored)
    {
        super(properties);
        this.extendedMag = extendedMag;
        this.colored = colored;
    }

    @Override
    public ExtendedMag getProperties()
    {
        return this.extendedMag;
    }

    @Override
    public boolean canColor(ItemStack stack)
    {
        return this.colored;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantments.BINDING_CURSE || super.canApplyAtEnchantingTable(stack, enchantment);
    }
}
