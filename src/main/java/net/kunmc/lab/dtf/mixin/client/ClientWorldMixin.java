package net.kunmc.lab.dtf.mixin.client;

//@Mixin(ClientWorld.class)
public class ClientWorldMixin {
  /*  @Inject(method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FF)V", at = @At("HEAD"))
    private void playSound(PlayerEntity player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, CallbackInfo ci) {
        addWave(new Vec3d(x, y, z), volume, pitch);
    }

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

    private void addWave(Vec3d vec3d, float range, float speed) {
        WaveRenderer.getInstance().waveing(vec3d, range * 10, Math.max(speed, 0.7f));
    }*/
}
