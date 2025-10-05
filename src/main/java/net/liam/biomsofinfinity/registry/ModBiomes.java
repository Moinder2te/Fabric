package net.liam.biomsofinfinity.registry;

import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.config.BOIConfig;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public final class ModBiomes {
    public static final RegistryKey<Biome> GLOWSHROOM_FOREST_KEY = RegistryKey.of(RegistryKeys.BIOME, BiomsOfInfinityMod.id("glowshroom_forest"));
    public static final RegistryKey<Biome> CRYSTAL_CLIFFS_KEY = RegistryKey.of(RegistryKeys.BIOME, BiomsOfInfinityMod.id("crystal_cliffs"));
    public static final RegistryKey<Biome> SHADOW_ISLES_KEY = RegistryKey.of(RegistryKeys.BIOME, BiomsOfInfinityMod.id("shadow_isles"));

    private ModBiomes() {}

    public static void init() {
        BOIConfig.Worldgen worldgen = BiomsOfInfinityMod.getConfig().worldgen;
        if (!worldgen.enableAllBiomes) {
            BiomsOfInfinityMod.LOGGER.info("Biom distribution disabled via config; skipping End biome injections.");
            return;
        }

        TheEndBiomes.addHighlandsBiome(GLOWSHROOM_FOREST_KEY, worldgen.getBiomeWeight("glowshroom_forest", 8));
        TheEndBiomes.addHighlandsBiome(CRYSTAL_CLIFFS_KEY, worldgen.getBiomeWeight("crystal_cliffs", 6));
        TheEndBiomes.addSmallIslandsBiome(SHADOW_ISLES_KEY, worldgen.getBiomeWeight("shadow_isles", 3));
    }

    public static int getRegisteredBiomeCount() {
        return 3;
    }
}
