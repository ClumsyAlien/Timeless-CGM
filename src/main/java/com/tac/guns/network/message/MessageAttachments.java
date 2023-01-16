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
public class MessageAttachments extends PlayMessage<MessageAttachments>
{
    public MessageAttachments() {}

    @Override
    public void encode(MessageAttachments messageAttachments, FriendlyByteBuf buffer) {}

    @Override
    public MessageAttachments decode(FriendlyByteBuf buffer) {
        return new MessageAttachments();
    }

    @Override
    public void handle(MessageAttachments messageAttachments, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ServerPlayHandler.handleAttachments(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
