package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.common.network.ServerPlayHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageColorBench extends PlayMessage<MessageColorBench>
{
    public MessageColorBench() {}

    @Override
    public void encode(MessageColorBench messageColorBench, FriendlyByteBuf buffer) {}

    @Override
    public MessageColorBench decode(FriendlyByteBuf buffer) {
        return new MessageColorBench();
    }

    @Override
    public void handle(MessageColorBench messageColorBench, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ServerPlayHandler.handleColorbenchGui(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
