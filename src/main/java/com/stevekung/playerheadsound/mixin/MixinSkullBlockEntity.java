package com.stevekung.playerheadsound.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.playerheadsound.PlayerHeadSound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

@Mixin(SkullBlockEntity.class)
public class MixinSkullBlockEntity implements PlayerHeadSound
{
    @Unique
    private SoundEvent soundEvent;

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void playerHeadSound$saveAdditional(CompoundTag compoundTag, CallbackInfo info)
    {
        if (this.soundEvent != null)
        {
            compoundTag.putString(TAG_SOUND_EVENT, BuiltInRegistries.SOUND_EVENT.getKey(this.soundEvent).toString());
        }
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void playerHeadSound$load(CompoundTag compoundTag, CallbackInfo info)
    {
        if (compoundTag.contains(TAG_SOUND_EVENT, Tag.TAG_STRING))
        {
            this.soundEvent = BuiltInRegistries.SOUND_EVENT.getOptional(new ResourceLocation(compoundTag.getString(TAG_SOUND_EVENT))).orElse(PlayerHeadSound.PLAYER_CLASS_HURT);
        }
    }

    @Override
    public void setSoundEvent(SoundEvent soundEvent)
    {
        this.soundEvent = soundEvent;
    }

    @Override
    public SoundEvent getSoundEvent()
    {
        return this.soundEvent;
    }
}