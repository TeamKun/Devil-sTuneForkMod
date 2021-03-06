package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.packet.WaveMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WaveMessageHandler {
    public static void reversiveMessage(WaveMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        WaveRenderer.getInstance().waveing(message.possion, message.nat ? message.range : message.range * 10, message.nat ? message.speed : Math.max(message.speed, 0.7f));
    }
}
