package net.liam.biomsofinfinity.content.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

public final class ShadowIslesBiome {
    private ShadowIslesBiome() {}

    public static Biome createDefault() {
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();
        spawn.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 30, 1, 4));

        GenerationSettings.Builder generation = new GenerationSettings.Builder();

        BiomeEffects effects = new BiomeEffects.Builder()
            .skyColor(0x201020)
            .fogColor(0x110511)
            .waterColor(0x1F1030)
            .waterFogColor(0x050505)
            .particleConfig(new BiomeParticleConfig(ParticleTypes.SMOKE, 0.01F))
            .loopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
            .moodSound(BiomeMoodSound.CAVE)
            .build();

        return new Biome.Builder()
            .precipitation(false)
            .temperature(0.4F)
            .downfall(0.0F)
            .effects(effects)
            .spawnSettings(spawn.build())
            .generationSettings(generation.build())
            .build();
    }
}
