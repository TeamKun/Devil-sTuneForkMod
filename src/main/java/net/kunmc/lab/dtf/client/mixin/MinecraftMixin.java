package net.kunmc.lab.dtf.client.mixin;

import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "updateWindowSize", at = @At("HEAD"), cancellable = true)
    private void updateWindowSize(CallbackInfo ci) {
        WaveRenderer waveRenderer = WaveRenderer.getInstance();
        if (waveRenderer.depthCopyFbo != 0) {
            waveRenderer.deleteDepthCopyFramebuffer();
        }
    }
}
