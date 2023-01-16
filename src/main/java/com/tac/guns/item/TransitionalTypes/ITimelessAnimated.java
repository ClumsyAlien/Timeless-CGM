package com.tac.guns.item.TransitionalTypes;

import net.minecraft.world.item.ItemStack;

/**
 * This Interface is also used for identifying the GunItem animated by Timeless.
 */
public interface ITimelessAnimated{
    void playAnimation(String animationName, ItemStack stack, boolean coercive);
}
