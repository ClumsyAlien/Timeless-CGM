package com.tac.guns.network.message;


import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.common.network.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */


public class MessageUpdatePlayerMovement extends PlayMessage<MessageUpdatePlayerMovement>
{
    @Override
    public void encode(MessageUpdatePlayerMovement messageUpdatePlayerMovement, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(messageUpdatePlayerMovement.handle);
    }
    @Override
    public MessageUpdatePlayerMovement decode(FriendlyByteBuf buffer)
    {
        return new MessageUpdatePlayerMovement(buffer.readBoolean());
    }
    public MessageUpdatePlayerMovement() {}
    private boolean handle;
    public MessageUpdatePlayerMovement(boolean handle)
    {
        this.handle = handle;
    }

    @Override
    public void handle(MessageUpdatePlayerMovement messageUpdatePlayerMovement, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ServerPlayHandler.handleMovementUpdate(supplier.get().getSender(), messageUpdatePlayerMovement.handle));
        //supplier.get().enqueueWork(() -> {ServerPlayHandler.handleMovementUpdateLow(supplier.get().getSender());});
        supplier.get().setPacketHandled(true);
    }
}


