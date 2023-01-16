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
public class MessageUnload extends PlayMessage<MessageUnload>
{
    @Override
    public void encode(MessageUnload messageUnload, FriendlyByteBuf buffer) {}

    @Override
    public MessageUnload decode(FriendlyByteBuf buffer) {
        return new MessageUnload();
    }

    @Override
    public void handle(MessageUnload messageUnload, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                ServerPlayHandler.handleUnload(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
