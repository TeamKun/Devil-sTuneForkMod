package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.packet.WaveActiveMessage;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WaveActiveMessageHandler {
    public static boolean ENABLE;

    public static void reversiveMessage(WaveActiveMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        ENABLE = message.active;
    }
}
