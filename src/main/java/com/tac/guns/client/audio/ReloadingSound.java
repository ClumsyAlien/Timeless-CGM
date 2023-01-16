package com.tac.guns.client.audio;

import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;

public class ReloadingSound extends EntityBoundSoundInstance {


    public ReloadingSound(SoundEvent sound, SoundSource category, Entity entity) {
        super(sound, category, 1.0F, 1.0F, entity);
    }
    @Override
    public void tick(){
        super.tick();
    }
}
