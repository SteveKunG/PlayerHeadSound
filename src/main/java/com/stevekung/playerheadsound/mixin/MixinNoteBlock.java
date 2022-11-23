package com.stevekung.playerheadsound.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.stevekung.playerheadsound.PlayerHeadSound;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(NoteBlock.class)
public class MixinNoteBlock
{
    @Redirect(method = "triggerEvent", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void playerHeadSound$playSound(Level level, @Nullable Player player, BlockPos blockPos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, BlockState blockState, Level _level, BlockPos _blockPos, int id, int param)
    {
        if (level.getBlockEntity(blockPos.above()) instanceof PlayerHeadSound head)
        {
            level.playSound(player, blockPos, head.getSoundEvent() != null ? head.getSoundEvent() : soundEvent, soundSource, volume, pitch);
        }
        else
        {
            level.playSound(player, blockPos, soundEvent, soundSource, volume, pitch);
        }
    }
}