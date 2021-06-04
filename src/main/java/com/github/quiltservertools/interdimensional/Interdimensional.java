package com.github.quiltservertools.interdimensional;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nucleoid.fantasy.Fantasy;


public class Interdimensional implements DedicatedServerModInitializer {
    public static Fantasy FANTASY;
    public static Logger LOGGER;
    public static Config CONFIG;

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::serverStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::serverStopping);
        LOGGER = LogManager.getLogger();
        CommandRegistrationCallback.EVENT.register(this::registerCommands);
    }

    private void serverStarting(MinecraftServer server) {
        FANTASY = Fantasy.get(server);
        CONFIG = Config.createConfig(FabricLoader.getInstance().getConfigDir().resolve("interdimensional.json"), server);
    }

    private void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
    }

    private void serverStopping(MinecraftServer server) {
        CONFIG.shutdown();
    }
}
