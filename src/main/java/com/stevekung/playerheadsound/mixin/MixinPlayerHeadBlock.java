package com.stevekung.playerheadsound.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.authlib.GameProfile;
import com.stevekung.playerheadsound.PlayerHeadSound;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PlayerHeadBlock.class)
public class MixinPlayerHeadBlock
{
    @Inject(method = "setPlacedBy", at = @At(value = "INVOKE", target = "net/minecraft/nbt/CompoundTag.contains(Ljava/lang/String;I)Z", ordinal = 0, shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void playerHeadSound$setSoundEvent(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack, CallbackInfo info, BlockEntity blockEntity, SkullBlockEntity skullBlockEntity, GameProfile gameProfile, CompoundTag compoundTag)
    {
        if (level.getBlockEntity(blockPos) instanceof PlayerHeadSound head && compoundTag.contains(PlayerHeadSound.TAG_SOUND_EVENT, Tag.TAG_STRING))
        {
            head.setSoundEvent(BuiltInRegistries.SOUND_EVENT.getOptional(new ResourceLocation(compoundTag.getString(PlayerHeadSound.TAG_SOUND_EVENT))).orElse(PlayerHeadSound.PLAYER_CLASS_HURT));
        }
    }
}