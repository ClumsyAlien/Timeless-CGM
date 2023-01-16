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


public class MessageEmptyMag extends PlayMessage<MessageEmptyMag>
{
    @Override
    public void encode(MessageEmptyMag messageEmptyMag, FriendlyByteBuf buffer) {}

    @Override
    public MessageEmptyMag decode(FriendlyByteBuf buffer) {
        return new MessageEmptyMag();
    }

    @Override
    public void handle(MessageEmptyMag messageEmptyMag, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                ServerPlayHandler.EmptyMag(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}


