package net.kunmc.lab.dtf.handler;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.kunmc.lab.dtf.command.DTFCommand;
import net.kunmc.lab.dtf.config.ServerConfig;
import net.kunmc.lab.dtf.packet.PacketHandler;
import net.kunmc.lab.dtf.packet.WaveActiveMessage;
import net.kunmc.lab.dtf.util.PlayerUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class ServerHandler {
   /* @SubscribeEvent
    public static void onPlaySoundEvent(PlaySoundAtEntityEvent e) {
    //    PacketHandler.sendWave(e.getEntity().world, e.getEntity().getPositionVec(), e.getVolume(), e.getPitch());
    }*/

    @SubscribeEvent
    public static void onServerStart(FMLServerStartingEvent e) {
        DTFCommand.registerCommand(e.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
        if (!e.getPlayer().world.isRemote) {
            if (e.getPlayer() instanceof ServerPlayerEntity && ServerConfig.Active.get()) {
                PlayerUtils.grantAdvancement(new ResourceLocation(DevilsTuneFork.MODID, "recipes/devilstunefork"), (ServerPlayerEntity) e.getPlayer());
            }
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) e.getPlayer()), new WaveActiveMessage(ServerConfig.Active.get()));
        }
    }
}
