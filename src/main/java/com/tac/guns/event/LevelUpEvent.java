package com.tac.guns.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * <p>Fired when a players weapon has leveled up</p>
 *
 * @author Forked from MrCrayfish, continued by Timeless devs
 */
public class LevelUpEvent extends PlayerEvent
{
    private final ItemStack stack;

    public LevelUpEvent(Player player, ItemStack stack)
    {
        super(player);
        this.stack = stack;
    }

    /**
     * @return The stack the player was holding when firing the gun
     */
    public ItemStack getStack()
    {
        return stack;
    }

    /**
     * @return Whether or not this event was fired on the client side
     */
    public boolean isClient()
    {
        return this.getPlayer().getCommandSenderWorld().isClientSide();
    }

    /**
     * <p>Fired before a players weapon has leveled up.</p>
     *
     * @author Ocelot
     */
    @Cancelable
    public static class Pre extends LevelUpEvent
    {
        public Pre(Player player, ItemStack stack)
        {
            super(player, stack);
        }
    }

    /**
     * <p>Fired after a players weapon has leveled up.</p>
     *
     * @author Ocelot
     */
    public static class Post extends LevelUpEvent
    {
        public Post(Player player, ItemStack stack)
        {
            super(player, stack);
        }
    }
}
