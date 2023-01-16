package com.tac.guns.item;

import com.tac.guns.item.ScopeItem;
import com.tac.guns.item.attachment.impl.Scope;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import com.tac.guns.item.attachment.IAttachment.Type;
import net.minecraft.world.item.Item.Properties;

/**
 * A basic scope attachment item implementation with color support
 *
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class PistolScopeItem extends ScopeItem
{
    public PistolScopeItem(Scope scope, Properties properties)
    {
        super(scope,properties);
    }
    @Override
    public Type getType()
    {
        return Type.PISTOL_SCOPE;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        tooltip.add((new TranslatableComponent("info.tac.pistolScope_type").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)));
    }
}
