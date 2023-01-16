package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.event.GunReloadEvent;
import com.tac.guns.init.ModSyncedDataKeys;
import com.tac.guns.item.GunItem;
import com.tac.guns.network.PacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageReload extends PlayMessage<MessageReload>
{
    private boolean reload;

    public MessageReload()
    {
    }

    public MessageReload(boolean reload)
    {
        this.reload = reload;
    }

    @Override
    public void encode(MessageReload messageReload, FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(messageReload.reload);
    }

    @Override
    public MessageReload decode(FriendlyByteBuf buffer)
    {
        return new MessageReload(buffer.readBoolean());
    }

    @Override
    public void handle(MessageReload messageReload, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if(player != null && !player.isSpectator())
            {
                ModSyncedDataKeys.RELOADING.setValue(player, messageReload.reload); // This has to be set in order to verify the packet is sent if the event is cancelled
                if(!messageReload.reload)
                    return;

                ItemStack gun = player.getMainHandItem();
                if(MinecraftForge.EVENT_BUS.post(new GunReloadEvent.Pre(player, gun)))
                {
                    ModSyncedDataKeys.RELOADING.setValue(player, false);
                    return;
                }
                MinecraftForge.EVENT_BUS.post(new GunReloadEvent.Post(player, gun));

                ResourceLocation reloadSound = ((GunItem)gun.getItem()).getGun().getSounds().getCock();
                if(reloadSound != null)
                {
                    MessageGunSound message = new MessageGunSound(reloadSound, SoundSource.PLAYERS, (float) player.getX(), (float) player.getY() + 1.0F, (float) player.getZ(), 1.0F, 1.0F, player.getId(), false, true);
                    PacketHandler.getPlayChannel().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player,player.getX(), (player.getY() + 1.0), player.getZ(), 16.0, player.level.dimension())), message);
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
