package com.stevekung.playerheadsound.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stevekung.playerheadsound.PlayerHeadSound;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

@Mixin(Minecraft.class)
public class MixinMinecraft
{
    @Inject(method = "addCustomNbtData", at = @At(value = "INVOKE", target = "net/minecraft/nbt/CompoundTag.put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void playerHeadSound$pickupSoundEvent(ItemStack itemStack, BlockEntity blockEntity, CallbackInfoReturnable<ItemStack> info, CompoundTag compoundTag, CompoundTag compoundTag2)
    {
        if (compoundTag.contains(PlayerHeadSound.TAG_SOUND_EVENT, Tag.TAG_STRING))
        {
            itemStack.getOrCreateTag().putString(PlayerHeadSound.TAG_SOUND_EVENT, compoundTag.getString(PlayerHeadSound.TAG_SOUND_EVENT));
        }
    }
}