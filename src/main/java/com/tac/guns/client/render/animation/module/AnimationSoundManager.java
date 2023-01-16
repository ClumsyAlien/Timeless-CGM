package com.tac.guns.client.render.animation.module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public enum AnimationSoundManager {
    INSTANCE;
    private final Map<UUID, Map<ResourceLocation, SoundInstance> > soundsMap = new HashMap<>();

    public void playerSound(Player player, AnimationMeta animationMeta, AnimationSoundMeta soundMeta){
        Map<ResourceLocation, SoundInstance> map = soundsMap.computeIfAbsent(player.getUUID(), k -> new HashMap<>());
        SoundInstance sound = map.get(animationMeta.getResourceLocation());
        if(sound == null) {
            SoundEvent soundEvent = new SoundEvent(soundMeta.getResourceLocation());
            sound = new EntityBoundSoundInstance(soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F, player);
        }
        if(sound instanceof EntityBoundSoundInstance){
            EntityBoundSoundInstance entityTickableSound = (EntityBoundSoundInstance) sound;
            if(entityTickableSound.isStopped()){
                SoundEvent soundEvent = new SoundEvent(soundMeta.getResourceLocation());
                sound = new EntityBoundSoundInstance(soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F, player);
            }
        }
        if(Minecraft.getInstance().getSoundManager().isActive(sound)) return;
        Minecraft.getInstance().getSoundManager().play(sound);
        map.put(animationMeta.getResourceLocation(), sound);
    }

    public void onPlayerDeath(Player player){
        Map<ResourceLocation, SoundInstance> map = soundsMap.computeIfAbsent(player.getUUID(), k -> new HashMap<>());
        for(SoundInstance sound : map.values()){
            Minecraft.getInstance().getSoundManager().stop(sound);
        }
        soundsMap.remove(player.getUUID());
    }

    public void interruptSound(Player player, AnimationMeta animationMeta){
        Map<ResourceLocation, SoundInstance> map = soundsMap.get(player.getUUID());
        if(map != null){
            SoundInstance sound = map.get(animationMeta.getResourceLocation());
            if(sound != null){
                Minecraft.getInstance().getSoundManager().stop(sound);
            }
            map.remove(animationMeta.getResourceLocation());
        }
    }
}
