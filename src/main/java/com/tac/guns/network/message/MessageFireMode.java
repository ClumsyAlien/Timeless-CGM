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


public class MessageFireMode extends PlayMessage<MessageFireMode>
{
    @Override
    public void encode(MessageFireMode messageFireMode, FriendlyByteBuf buffer) {}

    @Override
    public MessageFireMode decode(FriendlyByteBuf buffer) {
        return new MessageFireMode();
    }

    @Override
    public void handle(MessageFireMode messageFireMode, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                ServerPlayHandler.handleFireMode(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}


