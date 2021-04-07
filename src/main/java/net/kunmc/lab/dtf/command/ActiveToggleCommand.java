package net.kunmc.lab.dtf.command;

import com.mojang.brigadier.CommandDispatcher;
import net.kunmc.lab.dtf.DevilsTuneFork;
import net.kunmc.lab.dtf.config.ServerConfig;
import net.kunmc.lab.dtf.packet.PacketHandler;
import net.kunmc.lab.dtf.packet.WaveActiveMessage;
import net.kunmc.lab.dtf.util.PlayerUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class ActiveToggleCommand {
    public static void register(CommandDispatcher<CommandSource> d) {
        d.register(Commands.literal("dtf").requires((source) -> source.hasPermissionLevel(2)).then(Commands.literal("enable").executes(src -> setActive(src.getSource(), true))).then(Commands.literal("disable").executes(src -> setActive(src.getSource(), false))));
    }

    public static int setActive(CommandSource source, boolean active) {
        ServerConfig.Active.set(active);
        PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new WaveActiveMessage(active));
        source.sendFeedback(new TranslationTextComponent("commands.dtf.setactive." + active), true);
        if (active) {
            for (String onlinePlayerName : source.getServer().getPlayerList().getOnlinePlayerNames()) {
                ServerPlayerEntity player = source.getServer().getPlayerList().getPlayerByUsername(onlinePlayerName);
                PlayerUtils.grantAdvancement(new ResourceLocation(DevilsTuneFork.MODID, "recipes/devilstunefork"), player);
            }
        }
        return 1;
    }

    public static int showActive(CommandSource source) {
        source.sendFeedback(new TranslationTextComponent("commands.dtf.showactive." + ServerConfig.Active.get()), true);
        return 1;
    }
}
