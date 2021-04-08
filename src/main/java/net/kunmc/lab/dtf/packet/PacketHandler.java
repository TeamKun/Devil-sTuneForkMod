package net.kunmc.lab.dtf.packet;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.kunmc.lab.dtf.client.handler.WaveActiveMessageHandler;
import net.kunmc.lab.dtf.client.handler.WaveMessageHandler;
import net.kunmc.lab.dtf.config.ServerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
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
        INSTANCE.registerMessage(next(), WaveActiveMessage.class, WaveActiveMessage::encodeMessege, WaveActiveMessage::decodeMessege, WaveActiveMessageHandler::reversiveMessage);
    }

    public static void sendWave(World world, Vec3d pos, float range, float speed) {
        sendWave(world, pos, range, speed, false);
    }

    public static void sendWave(World world, Vec3d pos, float range, float speed, boolean nat) {
        if (!world.isRemote && ServerConfig.Active.get()) {
            Chunk ch = (Chunk) world.getChunk(new BlockPos(pos));
            PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch), new WaveMessage(pos, range, speed, nat));
        }
    }

    public static void sendSoundWave(PlayerEntity except, World world, double x, double y, double z, double radius, float range, float speed) {
        if (!world.isRemote && ServerConfig.Active.get()) {
            for (String onlinePlayerName : world.getServer().getPlayerList().getOnlinePlayerNames()) {
                ServerPlayerEntity playerEntity = world.getServer().getPlayerList().getPlayerByUsername(onlinePlayerName);
                if (!isNear(playerEntity, except, x, y, z, radius, world.getDimension().getType())) {
                    PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerEntity), new WaveMessage(new Vec3d(x, y, z), range, speed, false));
                }
            }
        }
    }

    private static boolean isNear(ServerPlayerEntity playerEntity, PlayerEntity except, double x, double y, double z, double radius, DimensionType dimension) {
        if (playerEntity != except && playerEntity.dimension == dimension) {
            double d0 = x - playerEntity.getPosX();
            double d1 = y - playerEntity.getPosY();
            double d2 = z - playerEntity.getPosZ();
            return d0 * d0 + d1 * d1 + d2 * d2 < radius * radius;
        }
        return false;
    }
}
