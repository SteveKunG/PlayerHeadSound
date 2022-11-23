package com.stevekung.playerheadsound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public interface PlayerHeadSound
{
    SoundEvent PLAYER_CLASS_HURT = register("entity.player.classic_hurt");
    String TAG_SOUND_EVENT = "SoundEvent";

    SoundEvent getSoundEvent();

    void setSoundEvent(SoundEvent soundEvent);

    private static SoundEvent register(String name)
    {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, name, new SoundEvent(new ResourceLocation(name)));
    }
}