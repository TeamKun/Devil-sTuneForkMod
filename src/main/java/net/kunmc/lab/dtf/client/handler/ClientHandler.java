package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    @SubscribeEvent
    public static void onPlaySoundEvent(PlaySoundEvent e) {
        if (e.getName().contains("explode")) {
            WaveRenderer.getInstance().addWaveTS(new Vec3d(e.getSound().getX(), e.getSound().getY(), e.getSound().getZ()), 1);
        }
    }
}
