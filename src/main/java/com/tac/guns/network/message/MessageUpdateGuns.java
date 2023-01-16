package com.tac.guns.network.message;

import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import com.tac.guns.common.CustomGun;
import com.tac.guns.common.CustomGunLoader;
import com.tac.guns.common.Gun;
import com.tac.guns.common.NetworkGunManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageUpdateGuns extends PlayMessage<MessageUpdateGuns> implements NetworkGunManager.IGunProvider
{
    private ImmutableMap<ResourceLocation, Gun> registeredGuns;
    private ImmutableMap<ResourceLocation, CustomGun> customGuns;

    public MessageUpdateGuns() {}

    public MessageUpdateGuns(ImmutableMap<ResourceLocation, Gun> readRegisteredGuns, ImmutableMap<ResourceLocation, CustomGun> readCustomGuns) {
        this.registeredGuns = readRegisteredGuns;
        this.customGuns = readCustomGuns;
    }

    @Override
    public void encode(MessageUpdateGuns messageUpdateGuns, FriendlyByteBuf buffer)
    {
        Validate.notNull(NetworkGunManager.get());
        Validate.notNull(CustomGunLoader.get());
        NetworkGunManager.get().writeRegisteredGuns(buffer);
        CustomGunLoader.get().writeCustomGuns(buffer);
    }

    @Override
    public MessageUpdateGuns decode(FriendlyByteBuf buffer)
    {
        return new MessageUpdateGuns(NetworkGunManager.readRegisteredGuns(buffer), CustomGunLoader.readCustomGuns(buffer));
    }

    @Override
    public void handle(MessageUpdateGuns messageUpdateGuns, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleUpdateGuns(messageUpdateGuns));
        supplier.get().setPacketHandled(true);
    }

    @Override
    public ImmutableMap<ResourceLocation, Gun> getRegisteredGuns()
    {
        return this.registeredGuns;
    }

    public ImmutableMap<ResourceLocation, CustomGun> getCustomGuns()
    {
        return this.customGuns;
    }
}
