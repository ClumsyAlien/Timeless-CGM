package com.tac.guns.crafting;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class ModRecipeType
{
    public static final RecipeType<WorkbenchRecipe> WORKBENCH = RecipeType.register("cgm:workbench");

    // Does nothing but trigger loading of static fields
    public static void init() {}
}
