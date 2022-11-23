package com.stevekung.playerheadsound.mixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.playerheadsound.PlayerHeadSound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

@Mixin(NoteBlockInstrument.class)
public class MixinNoteBlockInstrument
{
    @Shadow
    @Final
    @Mutable
    @SuppressWarnings("target")
    static NoteBlockInstrument[] $VALUES;

    @Invoker("<init>")
    static NoteBlockInstrument newNoteInstrument(String internalName, int internalId, String name, SoundEvent soundEvent, boolean isMobHead)
    {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "net/minecraft/world/level/block/state/properties/NoteBlockInstrument.$VALUES:[Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;", shift = At.Shift.AFTER))
    private static void playerHeadSound$addNewInstrument(CallbackInfo info)
    {
        var instruments = new ArrayList<>(Arrays.asList($VALUES));
        var last = instruments.get(instruments.size() - 1);
        instruments.add(newNoteInstrument("player", last.ordinal() + 1, "player", PlayerHeadSound.PLAYER_CLASS_HURT, true));
        $VALUES = instruments.toArray(NoteBlockInstrument[]::new);
    }

    @Inject(method = "byStateAbove", cancellable = true, at = @At("HEAD"))
    private static void playerHeadSound$addPlayerHeadState(BlockState blockState, CallbackInfoReturnable<Optional<NoteBlockInstrument>> info)
    {
        if (blockState.is(Blocks.PLAYER_HEAD))
        {
            info.setReturnValue(Optional.of(NoteBlockInstrument.valueOf("player")));
        }
    }
}