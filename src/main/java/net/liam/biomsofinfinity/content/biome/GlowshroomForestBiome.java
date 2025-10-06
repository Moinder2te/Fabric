package net.liam.biomsofinfinity.content.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public final class GlowshroomForestBiome {
    private GlowshroomForestBiome() {}

    public static Biome createDefault() {
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();
        spawn.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 20, 1, 4));

        GenerationSettings.Builder generation = new GenerationSettings.Builder();

        BiomeEffects effects = new BiomeEffects.Builder()
            .skyColor(0x4422FF)
            .fogColor(0x553388)
            .waterColor(0x3F76E4)
            .waterFogColor(0x050533)
            .particleConfig(new BiomeParticleConfig(ParticleTypes.GLOW, 0.01F))
            .loopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
            .moodSound(BiomeMoodSound.CAVE)
            .build();

        return new Biome.Builder()
            .precipitation(false)
            .temperature(0.8F)
            .downfall(0.0F)
            .effects(effects)
            .spawnSettings(spawn.build())
            .generationSettings(generation.build())
            .build();
    }
}
