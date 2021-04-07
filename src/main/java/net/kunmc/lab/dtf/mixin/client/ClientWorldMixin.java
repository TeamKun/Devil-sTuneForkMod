package net.kunmc.lab.dtf.mixin.client;

import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    /*
        @Inject(method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V", at = @At("HEAD"))
        private void playSound(PlayerEntity player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, CallbackInfo ci) {
            addWave(new Vec3d(x, y, z), volume, pitch);
        }
    */
    /*
       @Inject(method = "playMovingSound", at = @At("HEAD"))
       private void playMovingSound(PlayerEntity playerIn, Entity entityIn, SoundEvent eventIn, SoundCategory categoryIn, float volume, float pitch, CallbackInfo ci) {
           addWave(entityIn.getPositionVec(), volume, pitch);
       }


         @Inject(method = "playBroadcastSound", at = @At("HEAD"), cancellable = true)
         private void playBroadcastSound(int id, BlockPos pos, int data, CallbackInfo ci) {
             addWave(new Vec3d(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d), 0.5f, 1);
         }

         @Inject(method = "playEvent", at = @At("HEAD"), cancellable = true)
         private void playEvent(PlayerEntity player, int type, BlockPos pos, int data, CallbackInfo ci) {
             addWave(new Vec3d(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d), 0.5f, 1);
         }
     */
    @Inject(method = "playSound(DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V", at = @At("HEAD"), cancellable = true)
    private void playSound(double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay, CallbackInfo ci) {
        if (soundIn == SoundEvents.ENTITY_GENERIC_EXPLODE || soundIn == SoundEvents.BLOCK_LAVA_POP || soundIn == SoundEvents.BLOCK_LAVA_AMBIENT || soundIn == SoundEvents.BLOCK_FIRE_AMBIENT) {
            addWave(new Vec3d(x, y, z), volume, pitch);
        }
    }

    private void addWave(Vec3d vec3d, float range, float speed) {
        WaveRenderer.getInstance().waveing(vec3d, range * 10, Math.max(speed, 0.7f));
    }
}
