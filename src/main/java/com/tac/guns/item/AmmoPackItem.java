package com.tac.guns.item;

import com.tac.guns.Reference;
import com.tac.guns.inventory.AmmoPackCapabilityProvider;
import com.tac.guns.inventory.AmmoPackContainerProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class AmmoPackItem extends Item {

    public AmmoPackItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(world.isClientSide) return super.use(world, player, hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(player.getItemInHand(hand));
        AmmoPackContainerProvider containerProvider = new AmmoPackContainerProvider(player.getItemInHand(hand));
        NetworkHooks.openGui((ServerPlayer) player, containerProvider);
        super.use(world, player, hand);
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new AmmoPackCapabilityProvider();
    }
}
