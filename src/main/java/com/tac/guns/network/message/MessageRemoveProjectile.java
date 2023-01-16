package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageRemoveProjectile extends PlayMessage<MessageRemoveProjectile>
{
    private int entityId;

    public MessageRemoveProjectile() {}

    public MessageRemoveProjectile(int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public void encode(MessageRemoveProjectile messageRemoveProjectile, FriendlyByteBuf buffer)
    {
        buffer.writeInt(messageRemoveProjectile.entityId);
    }

    @Override
    public MessageRemoveProjectile decode(FriendlyByteBuf buffer)
    {
        return new MessageRemoveProjectile(buffer.readInt());
    }

    @Override
    public void handle(MessageRemoveProjectile messageRemoveProjectile, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleRemoveProjectile(messageRemoveProjectile));
        supplier.get().setPacketHandled(true);
    }

    public int getEntityId()
    {
        return this.entityId;
    }
}
