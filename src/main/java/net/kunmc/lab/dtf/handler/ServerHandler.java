package net.kunmc.lab.dtf.handler;

import net.kunmc.lab.dtf.command.DTFCommand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ServerHandler {
   /* @SubscribeEvent
    public static void onPlaySoundEvent(PlaySoundAtEntityEvent e) {
    //    PacketHandler.sendWave(e.getEntity().world, e.getEntity().getPositionVec(), e.getVolume(), e.getPitch());
    }*/

    @SubscribeEvent
    public static void onServerStart(FMLServerStartingEvent e) {
        DTFCommand.registerCommand(e.getCommandDispatcher());
    }
}
