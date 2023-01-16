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
public class MessageInspection extends PlayMessage<MessageInspection>
{
    public MessageInspection() {}

    @Override
    public void encode(MessageInspection messageInspection, FriendlyByteBuf buffer) {}

    @Override
    public MessageInspection decode(FriendlyByteBuf buffer) {
        return new MessageInspection();
    }

    @Override
    public void handle(MessageInspection messageInspection, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ServerPlayHandler.handleInspection(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
