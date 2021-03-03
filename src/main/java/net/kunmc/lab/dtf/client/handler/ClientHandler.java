package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onPlaySoundEvent(SoundEvent.SoundSourceEvent e) {
        WaveRenderer.getInstance().addWave(new Vec3d(e.getSound().getX(), e.getSound().getY(), e.getSound().getZ()), e.getSound().getVolume() * 10, e.getSound().getPitch());
    }
}
