package com.tac.guns.item.TransitionalTypes;


import com.tac.guns.Config;
import com.tac.guns.GunMod;
import com.tac.guns.common.Gun;
import com.tac.guns.interfaces.IGunModifier;
import com.tac.guns.item.GunItem;
import com.tac.guns.util.GunEnchantmentHelper;
import com.tac.guns.util.GunModifierHelper;
import com.tac.guns.util.Process;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.KeybindComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class TimelessGunItem extends GunItem {
    private final IGunModifier[] modifiers;
    private Boolean integratedOptic = false;

    /* public int getCurrFireMode() {return currFireMode;}
     public void cycleFireMode()
     {
         if(this.supportedFireModes.length-1 > this.currFireMode)
             this.currFireMode++;
         else if(Config.COMMON.gameplay.safetyExistence.get())
             this.currFireMode = 0;
         else
             this.currFireMode = 1;
     }
     private int currFireMode = Config.COMMON.gameplay.safetyExistence.get() ? 0 : 1;
     public void setSupportedFireModes(int[] arr) {this.supportedFireModes=arr;}
     public int[] getSupportedFireModes() {return supportedFireModes;}
     private int[] supportedFireModes = new int[]{};*/
    public TimelessGunItem(Process<Item.Properties> properties, IGunModifier... modifiers) {
        super(properties.process(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
        this.modifiers = modifiers;
    }

    public TimelessGunItem(Process<Item.Properties> properties, Boolean integratedOptic, IGunModifier... modifiers) {
        super(properties.process(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
        this.modifiers = modifiers;
        this.integratedOptic = integratedOptic;
    }

    public TimelessGunItem() {
        this(properties -> properties);
    }

    public Boolean isIntegratedOptic() {
        return integratedOptic;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        Gun modifiedGun = this.getModifiedGun(stack);
        Item ammo = ForgeRegistries.ITEMS.getValue(modifiedGun.getProjectile().getItem());
        if (ammo != null) {
            tooltip.add((new TranslatableComponent("info.tac.ammo_type", new TranslatableComponent(ammo.getDescriptionId()).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.DARK_GRAY)));
        }

        String additionalDamageText = "";
        CompoundTag tagCompound = stack.getTag();
        float additionalDamage;
        if (tagCompound != null && tagCompound.contains("AdditionalDamage", 99)) {
            additionalDamage = tagCompound.getFloat("AdditionalDamage");
            additionalDamage += GunModifierHelper.getAdditionalDamage(stack);
            if (additionalDamage > 0.0F) {
                additionalDamageText = ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage);
            } else if (additionalDamage < 0.0F) {
                additionalDamageText = ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage);
            }
        }

        additionalDamage = modifiedGun.getProjectile().getDamage();
        additionalDamage = GunModifierHelper.getModifiedProjectileDamage(stack, additionalDamage);
        additionalDamage = GunEnchantmentHelper.getAcceleratorDamage(stack, additionalDamage);
        tooltip.add((new TranslatableComponent("info.tac.damage", ChatFormatting.GOLD + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage) + additionalDamageText)).withStyle(ChatFormatting.DARK_GRAY));
        if (tagCompound != null) {
            if (tagCompound.getBoolean("IgnoreAmmo")) {
                tooltip.add((new TranslatableComponent("info.tac.ignore_ammo")).withStyle(ChatFormatting.AQUA));
            } else {
                int ammoCount = tagCompound.getInt("AmmoCount");
                tooltip.add((new TranslatableComponent("info.tac.ammo", ChatFormatting.GOLD.toString() + ammoCount + "/" + GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun))).withStyle(ChatFormatting.DARK_GRAY));
            }
        }

        if (tagCompound != null) {
            if (tagCompound.get("CurrentFireMode") == null) {
            } else if (tagCompound.getInt("CurrentFireMode") == 0)
                tooltip.add((new TranslatableComponent("info.tac.firemode_safe", (new KeybindComponent("key.tac.fireSelect")).getString().toUpperCase(Locale.ENGLISH))).withStyle(ChatFormatting.GREEN));
            else if (tagCompound.getInt("CurrentFireMode") == 1)
                tooltip.add((new TranslatableComponent("info.tac.firemode_semi", (new KeybindComponent("key.tac.fireSelect")).getString().toUpperCase(Locale.ENGLISH))).withStyle(ChatFormatting.RED));
            else if (tagCompound.getInt("CurrentFireMode") == 2)
                tooltip.add((new TranslatableComponent("info.tac.firemode_auto", (new KeybindComponent("key.tac.fireSelect")).getString().toUpperCase(Locale.ENGLISH))).withStyle(ChatFormatting.RED));
        }
        GunItem gun = (GunItem) stack.getItem();
        if (tagCompound != null) {
            float speed = 0.1f / (1 + ((gun.getGun().getGeneral().getWeightKilo() * (1 + GunModifierHelper.getModifierOfWeaponWeight(stack)) + GunModifierHelper.getAdditionalWeaponWeight(stack) - GunEnchantmentHelper.getWeightModifier(stack)) * 0.0275f));
            speed = Math.max(Math.min(speed, 0.095F), 0.075F);
            if (speed > 0.09)
                tooltip.add((new TranslatableComponent("info.tac.lightWeightGun", new TranslatableComponent(-((int) ((0.1 - speed) * 1000)) + "%").withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.DARK_AQUA)));
            else if (speed < 0.09 && speed > 0.0825)
                tooltip.add((new TranslatableComponent("info.tac.standardWeightGun", new TranslatableComponent(-((int) ((0.1 - speed) * 1000)) + "%").withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.DARK_GREEN)));
            else
                tooltip.add((new TranslatableComponent("info.tac.heavyWeightGun", new TranslatableComponent(-((int) ((0.1 - speed) * 1000)) + "%").withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.DARK_RED)));

            float percentageToNextLevel =
                    ( tagCompound.getFloat("levelDmg") * 100) / (modifiedGun.getGeneral().getLevelReq()*(((tagCompound.getInt("level"))*3.0f)));
            tooltip.add((new TranslatableComponent("info.tac.current_level").append(new TranslatableComponent( " " + tagCompound.getInt("level") + " : " + percentageToNextLevel+"%")))
                    .withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        }
        tooltip.add((new TranslatableComponent("info.tac.attachment_help", (new KeybindComponent("key.tac.attachments")).getString().toUpperCase(Locale.ENGLISH))).withStyle(ChatFormatting.YELLOW));
    }

    public IGunModifier[] getModifiers() {
        return this.modifiers;
    }


    @Override
    public int getBarColor(ItemStack stack) {
        return Objects.requireNonNull(ChatFormatting.GOLD.getColor());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (Config.CLIENT.display.weaponAmmoBar.get()) {
            CompoundTag tagCompound = stack.getOrCreateTag();
            Gun modifiedGun = this.getModifiedGun(stack);
            return !tagCompound.getBoolean("IgnoreAmmo") && tagCompound.getInt("AmmoCount") != GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun);
        } else return false;
    }

    @Override
    public boolean isFoil(ItemStack gunItem) {
        return false;
    }
}