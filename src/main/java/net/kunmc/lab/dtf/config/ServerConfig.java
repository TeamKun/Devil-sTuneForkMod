package net.kunmc.lab.dtf.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConfig {
    private static final Logger LOGGER = LogManager.getLogger(ServerConfig.class);
    public static ForgeConfigSpec.ConfigValue<Boolean> Active;

    public static void init() {
        Pair<ConfigLoder, ForgeConfigSpec> server_config = new ForgeConfigSpec.Builder().configure(ConfigLoder::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, server_config.getRight());
    }

    public static class ConfigLoder {
        public ConfigLoder(ForgeConfigSpec.Builder builder) {
            LOGGER.info("Loading Server Config");
            builder.push("Devil's Tune Fork");
            Active = builder.define("Active", false);
            builder.pop();
        }
    }
}
