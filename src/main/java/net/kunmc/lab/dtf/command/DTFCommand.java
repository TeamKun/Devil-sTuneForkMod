package net.kunmc.lab.dtf.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;

public class DTFCommand {
    public static void registerCommand(CommandDispatcher<CommandSource> d) {
        ActiveToggleCommand.register(d);
    }
}
