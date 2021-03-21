package net.kunmc.lab.dtf.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

public class WaveMessage {
    public Vec3d possion;
    public float range;
    public float speed;
    public boolean nat;

    public WaveMessage(Vec3d pos, float rage, float speed, boolean nat) {
        this.possion = pos;
        this.range = rage;
        this.speed = speed;
        this.nat = nat;
    }

    public static WaveMessage decodeMessege(PacketBuffer buffer) {

        return new WaveMessage(new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()), buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
    }

    public static void encodeMessege(WaveMessage messegeIn, PacketBuffer buffer) {
        buffer.writeDouble(messegeIn.possion.getX());
        buffer.writeDouble(messegeIn.possion.getY());
        buffer.writeDouble(messegeIn.possion.getZ());
        buffer.writeFloat(messegeIn.range);
        buffer.writeFloat(messegeIn.speed);
        buffer.writeBoolean(messegeIn.nat);
    }
}
