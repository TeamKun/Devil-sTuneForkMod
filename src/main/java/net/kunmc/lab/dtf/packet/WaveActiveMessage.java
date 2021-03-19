package net.kunmc.lab.dtf.packet;

import net.minecraft.network.PacketBuffer;

public class WaveActiveMessage {
    public boolean active;

    public WaveActiveMessage(boolean active) {
        this.active = active;
    }

    public static WaveActiveMessage decodeMessege(PacketBuffer buffer) {
        return new WaveActiveMessage(buffer.readBoolean());
    }

    public static void encodeMessege(WaveActiveMessage messegeIn, PacketBuffer buffer) {
        buffer.writeBoolean(messegeIn.active);
    }
}
