package net.liam.biomsofinfinity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Primary mod entrypoint. Keeps initialisation minimal while we stabilise the toolchain
 * for Minecraft 1.21.8. Additional systems will be registered here in follow-up updates.
 */
public final class BiomsOfInfinityMod implements ModInitializer {
    public static final String MOD_ID = "biomsofinfinity";
    public static final Logger LOGGER = LoggerFactory.getLogger("BiomsOfInfinity");

    @Override
    public void onInitialize() {
        LOGGER.info("Bioms of Infinity loaded using Fabric {} on Minecraft {}", FabricLoader.getInstance().getModContainer("fabricloader").map(container -> container.getMetadata().getVersion().getFriendlyString()).orElse("unknown"), FabricLoader.getInstance().getModContainer("minecraft").map(container -> container.getMetadata().getVersion().getFriendlyString()).orElse("unknown"));
    }
}
