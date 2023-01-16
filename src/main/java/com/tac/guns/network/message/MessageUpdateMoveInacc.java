package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.init.ModSyncedDataKeys;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageUpdateMoveInacc extends PlayMessage<MessageUpdateMoveInacc>
{
    private float dist = 0;

    public MessageUpdateMoveInacc() {}

    public MessageUpdateMoveInacc(float dist)
    {
        this.dist = dist;
    }

    @Override
    public void encode(MessageUpdateMoveInacc messageUpdateMoveInacc, FriendlyByteBuf buffer)
    {
        buffer.writeFloat(messageUpdateMoveInacc.dist);
    }

    @Override
    public MessageUpdateMoveInacc decode(FriendlyByteBuf buffer)
    {
        return new MessageUpdateMoveInacc(buffer.readFloat());
    }

    @Override
    public void handle(MessageUpdateMoveInacc messageUpdateMoveInacc, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ModSyncedDataKeys.MOVING.setValue(player, messageUpdateMoveInacc.dist);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
