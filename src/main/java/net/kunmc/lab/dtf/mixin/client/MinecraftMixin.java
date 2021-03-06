package net.kunmc.lab.dtf.mixin.client;

import net.kunmc.lab.dtf.client.handler.RenderHandler;
import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.client.renderer.WhiteLineRenderer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "updateWindowSize", at = @At("HEAD"), cancellable = true)
    private void updateWindowSize(CallbackInfo ci) {
        if (RenderHandler.actived) {
            WaveRenderer waveRenderer = WaveRenderer.getInstance();
            WhiteLineRenderer whiteLineRenderer = WhiteLineRenderer.getInstance();
            if (waveRenderer.depthCopyFbo != 0) {
                waveRenderer.deleteDepthCopyFramebuffer();
            }
            if (whiteLineRenderer.depthCopyFbo != 0) {
                whiteLineRenderer.deleteDepthCopyFramebuffer();
            }
        }
    }
}
