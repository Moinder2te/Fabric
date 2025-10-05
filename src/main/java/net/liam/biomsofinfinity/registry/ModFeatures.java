package net.liam.biomsofinfinity.registry;

import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class ModFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> GLOWSHROOM_PATCH_CONFIGURED = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, BiomsOfInfinityMod.id("glowshroom_patch"));
    public static final RegistryKey<PlacedFeature> GLOWSHROOM_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, BiomsOfInfinityMod.id("glowshroom_patch"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> SHADOW_BLOOM_PATCH_CONFIGURED = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, BiomsOfInfinityMod.id("shadow_bloom_patch"));
    public static final RegistryKey<PlacedFeature> SHADOW_BLOOM_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, BiomsOfInfinityMod.id("shadow_bloom_patch"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> CRYSTALITE_ORE_CONFIGURED = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, BiomsOfInfinityMod.id("crystalite_ore"));
    public static final RegistryKey<PlacedFeature> CRYSTALITE_ORE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, BiomsOfInfinityMod.id("crystalite_ore"));

    private ModFeatures() {}

    public static void init() {
        // Features are defined via data packs and referenced by keys.
    }
}
