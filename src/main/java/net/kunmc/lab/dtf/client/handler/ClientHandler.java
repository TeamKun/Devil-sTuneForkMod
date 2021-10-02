package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.api.DevilsTuneForkAPI;
import net.kunmc.lab.dtf.api.DevilsTuneForkEvent;
import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.client.renderer.WhiteLineRenderer;
import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.kunmc.lab.dtf.client.shader.WhiteLineShader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static long lastResize;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        DevilsTuneForkAPI api = DevilsTuneForkAPI.getInstance();
        if (mc.player == null || !api.isEnable()) {
            WaveShader.getInstance().removeAll();
            WhiteLineShader.getInstance().removeAll();
            WaveRenderer.getInstance().removeAllWave();
            WhiteLineRenderer.getInstance().removeAllWave();
        }
        if (System.currentTimeMillis() - lastResize > 1000 * 10) {
            api.resized();
            lastResize = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load e) {
        DevilsTuneForkAPI.getInstance().resized();
    }

    @SubscribeEvent
    public static void onDevilsTuneFork(DevilsTuneForkEvent e) {
        if (WaveActiveMessageHandler.ENABLE)
            e.setEnable();
    }

}
