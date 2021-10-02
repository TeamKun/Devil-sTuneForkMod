package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.api.DevilsTuneForkAPI;
import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.client.renderer.WhiteLineRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        if (DevilsTuneForkAPI.getInstance().isEnable()) {
            WhiteLineRenderer.getInstance().onRender(e);
            WaveRenderer.getInstance().onRender(e);
        }

        /*if (mc.gameRenderer.getShaderGroup() == null || !mc.gameRenderer.getShaderGroup().getShaderGroupName().equals(BLACK_WHITE_SHADER.toString())) {
            mc.gameRenderer.loadShader(BLACK_WHITE_SHADER);
        }*/
    }

}
