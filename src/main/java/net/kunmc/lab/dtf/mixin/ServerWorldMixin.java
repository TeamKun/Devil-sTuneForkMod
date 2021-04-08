package net.kunmc.lab.dtf.mixin;

import net.kunmc.lab.dtf.DTFSoundEvents;
import net.kunmc.lab.dtf.packet.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V", at = @At("HEAD"))
    private void playSound(PlayerEntity player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, CallbackInfo ci) {
        if (soundIn != DTFSoundEvents.USEDSOUND) {
            PacketHandler.sendSoundWave(player, (ServerWorld) (Object) this, x, y, z, volume > 1.0F ? (double) (16.0F * volume) : 16.0D, volume, pitch);
        }
    }

    @Inject(method = "playMovingSound", at = @At("HEAD"))
    private void playMovingSound(PlayerEntity playerIn, Entity entityIn, SoundEvent eventIn, SoundCategory categoryIn, float volume, float pitch, CallbackInfo ci) {
        PacketHandler.sendSoundWave(playerIn, (ServerWorld) (Object) this, entityIn.getPositionVec().x, entityIn.getPositionVec().y, entityIn.getPositionVec().z, volume > 1.0F ? (double) (16.0F * volume) : 16.0D, volume, pitch);
    }

    @Inject(method = "playBroadcastSound", at = @At("HEAD"), cancellable = true)
    private void playBroadcastSound(int id, BlockPos pos, int data, CallbackInfo ci) {
        PacketHandler.sendWave((ServerWorld) (Object) this, new Vec3d(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d), 0.5f, 1);
    }
/*
    @Inject(method = "playEvent", at = @At("HEAD"), cancellable = true)
    private void playEvent(PlayerEntity player, int type, BlockPos pos, int data, CallbackInfo ci) {
       // PacketHandler.sendSoundWave(null, (ServerWorld) (Object) this, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, 64.0D, 0.5f, 1);
    }*/

}
