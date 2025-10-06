package net.liam.biomsofinfinity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.liam.biomsofinfinity.command.BOICommand;
import net.liam.biomsofinfinity.config.BOIConfig;
import net.liam.biomsofinfinity.registry.ModAdvancements;
import net.liam.biomsofinfinity.registry.ModBiomes;
import net.liam.biomsofinfinity.registry.ModBlocks;
import net.liam.biomsofinfinity.registry.ModBlockEntities;
import net.liam.biomsofinfinity.registry.ModEntities;
import net.liam.biomsofinfinity.registry.ModFeatures;
import net.liam.biomsofinfinity.registry.ModItems;
import net.liam.biomsofinfinity.registry.ModParticles;
import net.liam.biomsofinfinity.registry.ModSounds;
import net.liam.biomsofinfinity.registry.ModWorldgen;
import net.liam.biomsofinfinity.persistent.BossCooldownManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiomsOfInfinityMod implements ModInitializer {
    public static final String MOD_ID = "biomsofinfinity";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static BOIConfig config;

    @Override
    public void onInitialize() {
        config = BOIConfig.load();

        ModSounds.init();
        ModParticles.init();
        ModBlocks.init();
        ModBlockEntities.init();
        ModItems.init();
        ModEntities.init();
        ModFeatures.init();
        ModBiomes.init();
        ModWorldgen.init();
        ModAdvancements.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> BOICommand.register(dispatcher));

        ServerLifecycleEvents.SERVER_STARTING.register(server -> BossCooldownManager.get(server).warmup(config));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> BOIConfig.save(config));

        LOGGER.info("Bioms of Infinity initialised: {} blocks, {} items, {} biomes, {} bosses", ModBlocks.getRegisteredBlockCount(), ModItems.getRegisteredItemCount(), ModBiomes.getRegisteredBiomeCount(), ModEntities.getRegisteredBossCount());
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static BOIConfig getConfig() {
        return config;
    }

    public static void reloadConfig() {
        config = BOIConfig.load();
    }

    public static void updateConfig(BOIConfig newConfig) {
        config = newConfig;
        BOIConfig.save(config);
    }

    public static void withServer(MinecraftServer server, Runnable runnable) {
        if (server != null) {
            runnable.run();
        }
    }
}
