package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.kunmc.lab.dtf.client.shader.WhiteLineShader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        if (mc.player == null) {
            WaveShader.getInstance().removeAll();
            WhiteLineShader.getInstance().removeAll();
        }
    }
}
