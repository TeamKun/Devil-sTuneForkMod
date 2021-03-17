package net.kunmc.lab.dtf.packet;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.kunmc.lab.dtf.client.handler.WaveMessageHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(DevilsTuneFork.MODID, DevilsTuneFork.MODID + "_channel")).clientAcceptedVersions(a -> true).serverAcceptedVersions(a -> true).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    private static int integer = -1;

    private static int next() {
        integer++;
        return integer;
    }

    public static void init() {
        INSTANCE.registerMessage(next(), WaveMessage.class, WaveMessage::encodeMessege, WaveMessage::decodeMessege, WaveMessageHandler::reversiveMessage);
    }

    public static void sendWave(World world, Vec3d pos, float range, float speed) {
        if (!world.isRemote) {
            Chunk ch = (Chunk) world.getChunk(new BlockPos(pos));
            PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch), new WaveMessage(pos, range, speed));
        }
    }
}
