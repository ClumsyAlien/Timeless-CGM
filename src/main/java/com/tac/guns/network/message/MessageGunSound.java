package com.tac.guns.network.message;

import com.mrcrayfish.framework.api.network.PlayMessage;
import com.tac.guns.client.network.ClientPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: Forked from MrCrayfish, continued by Timeless devs
 */
public class MessageGunSound extends PlayMessage<MessageGunSound>
{
    private ResourceLocation id;
    private SoundSource category;
    private float x;
    private float y;
    private float z;
    private float volume;
    private float pitch;
    private int shooterId;
    private boolean muzzle;
    private boolean reload;

    public MessageGunSound() {}

    public MessageGunSound(ResourceLocation id, SoundSource category, float x, float y, float z, float volume, float pitch, int shooterId, boolean muzzle, boolean reload)
    {
        this.id = id;
        this.category = category;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
        this.shooterId = shooterId;
        this.muzzle = muzzle;
        this.reload = reload;
    }

    @Override
    public void encode(MessageGunSound messageGunSound, FriendlyByteBuf buffer)
    {
        buffer.writeUtf(messageGunSound.id.toString());
        buffer.writeEnum(messageGunSound.category);
        buffer.writeFloat(messageGunSound.x);
        buffer.writeFloat(messageGunSound.y);
        buffer.writeFloat(messageGunSound.z);
        buffer.writeFloat(messageGunSound.volume);
        buffer.writeFloat(messageGunSound.pitch);
        buffer.writeInt(messageGunSound.shooterId);
        buffer.writeBoolean(messageGunSound.muzzle);
        buffer.writeBoolean(messageGunSound.reload);
    }

    @Override
    public MessageGunSound decode(FriendlyByteBuf buffer)
    {
        return new MessageGunSound(
                ResourceLocation.tryParse(buffer.readUtf()),
                buffer.readEnum(SoundSource.class),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readInt(),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    @Override
    public void handle(MessageGunSound messageGunSound, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() -> ClientPlayHandler.handleMessageGunSound(messageGunSound));
        supplier.get().setPacketHandled(true);
    }

    public ResourceLocation getId()
    {
        return this.id;
    }

    public SoundSource getCategory()
    {
        return this.category;
    }

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public float getZ()
    {
        return this.z;
    }

    public float getVolume()
    {
        return this.volume;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public int getShooterId()
    {
        return this.shooterId;
    }

    public boolean showMuzzleFlash()
    {
        return this.muzzle;
    }

    public boolean isReload() {
        return this.reload;
    }
}
