package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.common.NetworkGunManager;
import com.tac.guns.common.network.ServerPlayHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageUpdateGunID extends PlayMessage<MessageUpdateGunID>
{
    private boolean regenerate;
    public MessageUpdateGunID(boolean regenerate) {
        this.regenerate = regenerate;
    }
    public MessageUpdateGunID() {
        this.regenerate=false;
    }

    @Override
    public void encode(MessageUpdateGunID messageUpdateGunID, FriendlyByteBuf buffer) {}

    @Override
    public MessageUpdateGunID decode(FriendlyByteBuf buffer) {
        return new MessageUpdateGunID();
    }

    @Override
    public void handle(MessageUpdateGunID messageUpdateGunID, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                try
                {
                    Validate.notNull(NetworkGunManager.get());
                    ServerPlayHandler.handleGunID(player,messageUpdateGunID.regenerate);
                }
                catch (Exception e)
                {

                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
