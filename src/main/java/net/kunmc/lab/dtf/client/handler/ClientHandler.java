package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.client.renderer.WhiteLineRenderer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    @SubscribeEvent
    public static void onPlaySoundEvent(SoundEvent.SoundSourceEvent e) {
        WhiteLineRenderer.getInstance().addWave(new Vec3d(e.getSound().getX(), e.getSound().getY(), e.getSound().getZ()), e.getSound().getVolume() * 10, Math.max(e.getSound().getPitch(), 0.7f));
        WaveRenderer.getInstance().addWave(new Vec3d(e.getSound().getX(), e.getSound().getY(), e.getSound().getZ()), e.getSound().getVolume() * 10, Math.max(e.getSound().getPitch(), 0.7f));
    }
}
