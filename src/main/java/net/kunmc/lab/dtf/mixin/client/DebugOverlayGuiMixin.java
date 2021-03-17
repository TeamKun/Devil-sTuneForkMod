package net.kunmc.lab.dtf.mixin.client;

import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugOverlayGui.class)
public class DebugOverlayGuiMixin {
    @Inject(method = "getDebugInfoLeft", at = @At("RETURN"), cancellable = true)
    private void getDebugInfoLeft(CallbackInfoReturnable<List<String>> cir) {
        cir.getReturnValue().add(String.format("Waves: %d/%d", WaveShader.getInstance().currentCont(), WaveShader.getInstance().maxCont()));
    }
}
