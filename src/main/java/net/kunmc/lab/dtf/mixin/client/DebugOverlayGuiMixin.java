package net.kunmc.lab.dtf.mixin.client;

import net.kunmc.lab.dtf.client.handler.RenderHandler;
import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(DebugOverlayGui.class)
public class DebugOverlayGuiMixin {
    @Inject(method = "getDebugInfoLeft", at = @At("RETURN"), cancellable = true)
    private void getDebugInfoLeft(CallbackInfoReturnable<List<String>> cir) {
        List<String> list = new ArrayList<>();
        list.add(String.format("Waves: %d/%d", WaveShader.getInstance().currentCont(), WaveShader.getInstance().maxCont()));
        list.add(String.format("Waves Active: %s", I18n.format(RenderHandler.actived ? "gui.yes" : "gui.no")));
        cir.getReturnValue().addAll(list);
    }
}
