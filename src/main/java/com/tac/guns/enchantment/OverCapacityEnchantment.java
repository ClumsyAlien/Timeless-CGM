package com.tac.guns.enchantment;

import net.minecraft.inventory.EquipmentSlotType;

/**
 * Author: MrCrayfish
 */
public class OverCapacityEnchantment extends GunEnchantment
{
    public OverCapacityEnchantment()
    {
        super(Rarity.RARE, EnchantmentTypes.GUN, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}, Type.WEAPON);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public int getMinEnchantability(int level)
    {
        return 5 + (level - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int level)
    {
        return super.getMinEnchantability(level) + 50;
    }
}
