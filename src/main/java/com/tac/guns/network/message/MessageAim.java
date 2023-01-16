package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.init.ModSyncedDataKeys;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PlayMessages;

import java.util.function.Supplier;

public class MessageAim extends PlayMessage<MessageAim> {
	private boolean aiming;

	public MessageAim() {}

	public MessageAim(boolean aiming)
	{
		this.aiming = aiming;
	}

	@Override
	public void encode(MessageAim message, FriendlyByteBuf buffer)
	{
		buffer.writeBoolean(message.aiming);
	}

	@Override
	public MessageAim decode(FriendlyByteBuf buffer)
	{
		return new MessageAim(buffer.readBoolean());
	}

	@Override
	public void handle(MessageAim messageAim, Supplier<NetworkEvent.Context> supplier) {
		supplier.get().enqueueWork(() ->
		{
			ServerPlayer player = supplier.get().getSender();
			if(player != null && !player.isSpectator())
			{
				ModSyncedDataKeys.AIMING.setValue(player, messageAim.aiming);
			}
		});
		supplier.get().setPacketHandled(true);
	}

}
