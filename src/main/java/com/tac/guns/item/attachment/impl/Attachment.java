package com.tac.guns.item.attachment.impl;

import com.tac.guns.Reference;
import com.tac.guns.interfaces.IGunModifier;
import com.tac.guns.item.attachment.IAttachment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * The base attachment object
 *
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public abstract class Attachment
{
    private final IGunModifier[] modifiers;
    private List<ITextComponent> perks = null;

    Attachment(IGunModifier... modifiers)
    {
        this.modifiers = modifiers;
    }

    public IGunModifier[] getModifiers()
    {
        return this.modifiers;
    }

    void setPerks(List<ITextComponent> perks)
    {
        if(this.perks == null)
        {
            this.perks = perks;
        }
    }

    List<ITextComponent> getPerks()
    {
        return this.perks;
    }

    /* Determines the perks of attachments and caches them */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void addInformationEvent(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof IAttachment<?>)
        {
            IAttachment<?> attachment = (IAttachment<?>) stack.getItem();
            List<ITextComponent> perks = attachment.getProperties().getPerks();
            if(perks != null && perks.size() > 0)
            {
                event.getToolTip().add(new TranslationTextComponent("perk.tac.title").withStyle(TextFormatting.GRAY, TextFormatting.BOLD));
                event.getToolTip().addAll(perks);
                return;
            }

            IGunModifier[] modifiers = attachment.getProperties().getModifiers();
            List<ITextComponent> positivePerks = new ArrayList<>();
            List<ITextComponent> negativePerks = new ArrayList<>();

            /* Test for fire sound volume */
            float inputSound = 1.0F;
            float outputSound = inputSound;
            for(IGunModifier modifier : modifiers)
            {
                outputSound = modifier.modifyFireSoundVolume(outputSound);
            }
            if(outputSound > inputSound)
            {
                addPerk(negativePerks, false, "perk.tac.fire_volume.negative");
            }
            else if(outputSound < inputSound)
            {
                addPerk(positivePerks, true, "perk.tac.fire_volume.positive");
            }

            /* Test for silenced */
            for(IGunModifier modifier : modifiers)
            {
                if(modifier.silencedFire())
                {
                    addPerk(positivePerks, true, "perk.tac.silenced.positive");
                    break;
                }
            }

            /* Test for sound radius */
            double inputRadius = 10.0;
            double outputRadius = inputRadius;
            for(IGunModifier modifier : modifiers)
            {
                outputRadius = modifier.modifyFireSoundRadius(outputRadius);
            }
            if(outputRadius > inputRadius)
            {
                addPerk(negativePerks, false, "perk.tac.sound_radius.negative");
            }
            else if(outputRadius < inputRadius)
            {
                addPerk(positivePerks, true, "perk.tac.sound_radius.positive");
            }

            /* Test for additional damage */
            float additionalDamage = 0.0F;
            for(IGunModifier modifier : modifiers)
            {
                additionalDamage += modifier.additionalDamage();
            }
            if(additionalDamage > 0.0F)
            {
                addPerk(positivePerks, true, "perk.tac.additional_damage.positive", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage / 2.0));
            }
            else if(additionalDamage < 0.0F)
            {
                addPerk(negativePerks, false, "perk.tac.additional_damage.negative", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(additionalDamage / 2.0));
            }

            /* Test for modified damage */
            float inputDamage = 10.0F;
            float outputDamage = inputDamage;
            for(IGunModifier modifier : modifiers)
            {
                outputDamage = modifier.modifyProjectileDamage(outputDamage);
            }
            if(outputDamage > inputDamage)
            {
                addPerk(positivePerks, true, "perk.tac.modified_damage.positive");
            }
            else if(outputDamage < inputDamage)
            {
                addPerk(negativePerks, false, "perk.tac.modified_damage.negative");
            }

            /* Test for modified damage */
            double inputSpeed = 10.0;
            double outputSpeed = inputSpeed;
            for(IGunModifier modifier : modifiers)
            {
                outputSpeed = modifier.modifyProjectileSpeed(outputSpeed);
            }
            if(outputSpeed > inputSpeed)
            {
                addPerk(positivePerks, true, "perk.tac.projectile_speed.positive");
            }
            else if(outputSpeed < inputSpeed)
            {
                addPerk(negativePerks, false, "perk.tac.projectile_speed.negative");
            }

            /* Test for modified projectile spread */
            float inputSpread = 10.0F;
            float outputSpread = inputSpread;
            for(IGunModifier modifier : modifiers)
            {
                outputSpread = modifier.modifyProjectileSpread(outputSpread);
            }
            if(outputSpread > inputSpread)
            {
                addPerk(negativePerks, false, "perk.tac.projectile_spread.negative");
            }
            else if(outputSpread < inputSpread)
            {
                addPerk(positivePerks, true, "perk.tac.projectile_spread.positive");
            }

            /* Test for modified projectile life */
            int inputLife = 100;
            int outputLife = inputLife;
            for(IGunModifier modifier : modifiers)
            {
                outputLife = modifier.modifyProjectileLife(outputLife);
            }
            if(outputLife > inputLife)
            {
                addPerk(positivePerks, true, "perk.tac.projectile_life.positive");
            }
            else if(outputLife < inputLife)
            {
                addPerk(negativePerks, false, "perk.tac.projectile_life.negative");
            }

            /* Test for modified recoil */
            float inputRecoil = 10.0F;
            float outputRecoil = inputRecoil;
            for(IGunModifier modifier : modifiers)
            {
                outputRecoil *= modifier.recoilModifier();
            }
            if(outputRecoil > inputRecoil)
            {
                addPerk(negativePerks, false, "perk.tac.recoil.negative");
            }
            else if(outputRecoil < inputRecoil)
            {
                addPerk(positivePerks, true, "perk.tac.recoil.positive");
            }

            /* Test for aim down sight speed */
            double inputAdsSpeed = 10.0;
            double outputAdsSpeed = inputAdsSpeed;
            for(IGunModifier modifier : modifiers)
            {
                outputAdsSpeed = modifier.modifyAimDownSightSpeed(outputAdsSpeed);
            }
            if(outputAdsSpeed > inputAdsSpeed)
            {
                addPerk(positivePerks, true, "perk.tac.ads_speed.positive");
            }
            else if(outputAdsSpeed < inputAdsSpeed)
            {
                addPerk(negativePerks, false, "perk.tac.ads_speed.negative");
            }

            /* Test for fire rate */
            int inputRate = 10;
            int outputRate = inputRate;
            for(IGunModifier modifier : modifiers)
            {
                outputRate = modifier.modifyFireRate(outputRate);
            }
            if(outputRate > inputRate)
            {
                addPerk(negativePerks, false, "perk.tac.rate.negative");
            }
            else if(outputRate < inputRate)
            {
                addPerk(positivePerks, true, "perk.tac.rate.positive");
            }

            positivePerks.addAll(negativePerks);
            attachment.getProperties().setPerks(positivePerks);
            if(positivePerks.size() > 0)
            {
                event.getToolTip().add(new TranslationTextComponent("perk.tac.title").withStyle(TextFormatting.GRAY, TextFormatting.BOLD));
                event.getToolTip().addAll(positivePerks);
            }
        }
    }

    private static void addPerk(List<ITextComponent> components, boolean positive, String id, Object... params)
    {
        components.add(new TranslationTextComponent(positive ? "perk.tac.entry.positive" : "perk.tac.entry.negative", new TranslationTextComponent(id, params).withStyle(TextFormatting.WHITE)).withStyle(positive ? TextFormatting.DARK_AQUA : TextFormatting.GOLD));
    }
}
