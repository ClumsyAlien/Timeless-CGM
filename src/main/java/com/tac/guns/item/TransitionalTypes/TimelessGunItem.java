package com.tac.guns.item.TransitionalTypes;


import com.tac.guns.Config;
import com.tac.guns.GunMod;
import com.tac.guns.common.Gun;
import com.tac.guns.item.GunItem;
import com.tac.guns.util.GunEnchantmentHelper;
import com.tac.guns.util.GunModifierHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import com.tac.guns.util.Process;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class TimelessGunItem extends GunItem {
    public TimelessGunItem(Process<Item.Properties> properties)
    {
        super(properties.process(new Item.Properties().stacksTo(1).tab(GunMod.GROUP)));
    }
    
    public TimelessGunItem() {
        this(properties -> properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        Gun modifiedGun = this.getModifiedGun(stack);
        Item ammo = (Item)ForgeRegistries.ITEMS.getValue(modifiedGun.getProjectile().getItem());
        if (ammo != null) {
            tooltip.add((new TranslationTextComponent("info.tac.ammo_type", new TranslationTextComponent(ammo.getDescriptionId()).withStyle(TextFormatting.GOLD)).withStyle(TextFormatting.DARK_GRAY)));
        }

        String additionalDamageText = "";
        CompoundNBT tagCompound = stack.getTag();
        float additionalDamage;
        if (tagCompound != null && tagCompound.contains("AdditionalDamage", 99)) {
            additionalDamage = tagCompound.getFloat("AdditionalDamage");
            additionalDamage += GunModifierHelper.getAdditionalDamage(stack);
            if (additionalDamage > 0.0F) {
                additionalDamageText = TextFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format((double)additionalDamage);
            } else if (additionalDamage < 0.0F) {
                additionalDamageText = TextFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format((double)additionalDamage);
            }
        }

        additionalDamage = modifiedGun.getProjectile().getDamage();
        additionalDamage = GunModifierHelper.getModifiedProjectileDamage(stack, additionalDamage);
        additionalDamage = GunEnchantmentHelper.getAcceleratorDamage(stack, additionalDamage);
        tooltip.add((new TranslationTextComponent("info.tac.damage", new Object[]{TextFormatting.GOLD + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format((double)additionalDamage) + additionalDamageText})).withStyle(TextFormatting.DARK_GRAY));
        if (tagCompound != null) {
            if (tagCompound.getBoolean("IgnoreAmmo")) {
                tooltip.add((new TranslationTextComponent("info.tac.ignore_ammo")).withStyle(TextFormatting.AQUA));
            } else {
                int ammoCount = tagCompound.getInt("AmmoCount");
                tooltip.add((new TranslationTextComponent("info.tac.ammo", new Object[]{TextFormatting.GOLD.toString() + ammoCount + "/" + GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun)})).withStyle(TextFormatting.DARK_GRAY));
            }
        }

        if(tagCompound.get("CurrentFireMode") == null)
        { }
        else if(tagCompound.getInt("CurrentFireMode") == 0)
            tooltip.add((new TranslationTextComponent("info.tac.firemode_safe", new Object[]{(new KeybindTextComponent("key.tac.fireSelect")).getString().toUpperCase(Locale.ENGLISH)})).withStyle(TextFormatting.GREEN));
        else if(tagCompound.getInt("CurrentFireMode") == 1)
            tooltip.add((new TranslationTextComponent("info.tac.firemode_semi", new Object[]{(new KeybindTextComponent("key.tac.fireSelect")).getString().toUpperCase(Locale.ENGLISH)})).withStyle(TextFormatting.RED));
        else if(tagCompound.getInt("CurrentFireMode") == 2)
            tooltip.add((new TranslationTextComponent("info.tac.firemode_auto", new Object[]{(new KeybindTextComponent("key.tac.fireSelect")).getString().toUpperCase(Locale.ENGLISH)})).withStyle(TextFormatting.RED));

        tooltip.add((new TranslationTextComponent("info.tac.attachment_help", new Object[]{(new KeybindTextComponent("key.tac.attachments")).getString().toUpperCase(Locale.ENGLISH)})).withStyle(TextFormatting.YELLOW));

    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return (Integer)Objects.requireNonNull(TextFormatting.GOLD.getColor());
    }
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        if (Config.CLIENT.display.weaponAmmoBar.get()) {
            CompoundNBT tagCompound = stack.getOrCreateTag();
            Gun modifiedGun = this.getModifiedGun(stack);
            return !tagCompound.getBoolean("IgnoreAmmo") && tagCompound.getInt("AmmoCount") != GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun);
        }
        else
            return false;
    }

    // Testing for hasEffect mapping
    @Override
    public boolean verifyTagAfterLoad(CompoundNBT p_179215_1_) {
        return false;
    }
    @Override
    public boolean is(ITag<Item> p_206844_1_) {
        return p_206844_1_.contains(this);
    }
}