package net.kunmc.lab.dtf.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kunmc.lab.dtf.config.ServerConfig;
import net.kunmc.lab.dtf.packet.PacketHandler;
import net.kunmc.lab.dtf.packet.WaveActiveMessage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class ActiveToggleCommand {
    public static void register(CommandDispatcher<CommandSource> d) {
        LiteralCommandNode<CommandSource> literalcommandnode = d.register(Commands.literal("devilstunefork").requires((source) -> source.hasPermissionLevel(2)).then(Commands.literal("active").executes(src -> showActive(src.getSource())).then(Commands.argument("active", BoolArgumentType.bool()).executes(src -> setActive(src.getSource(), BoolArgumentType.getBool(src, "active"))))));
        d.register(Commands.literal("dtf").requires((p_200556_0_) -> p_200556_0_.hasPermissionLevel(2)).redirect(literalcommandnode));
    }

    public static int setActive(CommandSource source, boolean active) {
        ServerConfig.Active.set(active);
        PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new WaveActiveMessage(active));
        source.sendFeedback(new TranslationTextComponent("commands.dtf.setactive." + active), true);
        return 1;
    }

    public static int showActive(CommandSource source) {
        source.sendFeedback(new TranslationTextComponent("commands.dtf.showactive." + ServerConfig.Active.get()), true);
        return 1;
    }
}
