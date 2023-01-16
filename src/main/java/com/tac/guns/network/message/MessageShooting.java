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
public class MessageShooting extends PlayMessage<MessageShooting>
{
    private boolean shooting;

    public MessageShooting() {}

    public MessageShooting(boolean shooting)
    {
        this.shooting = shooting;
    }

    @Override
    public void encode(MessageShooting messageShooting, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(messageShooting.shooting);
    }

    @Override
    public MessageShooting decode(FriendlyByteBuf buffer)
    {
        return new MessageShooting(buffer.readBoolean());
    }

    @Override
    public void handle(MessageShooting messageShooting, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ModSyncedDataKeys.SHOOTING.setValue(player, messageShooting.shooting);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
