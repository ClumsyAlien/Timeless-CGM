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
public class MessageLightChange extends PlayMessage<MessageLightChange>
{
    private int[] range;

    public MessageLightChange() {}

    public MessageLightChange(int[] range)
    {
        this.range = range;
    }

    @Override
    public void encode(MessageLightChange messageLightChange, FriendlyByteBuf buffer)
    {
        buffer.writeVarIntArray(messageLightChange.range);
    }

    @Override
    public MessageLightChange decode(FriendlyByteBuf buffer)
    {
        return new MessageLightChange(buffer.readVarIntArray());
    }

    @Override
    public void handle(MessageLightChange messageLightChange, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null)
            {
                ServerPlayHandler.handleFlashLight(player, this.range);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
