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

public final class CrystalCliffsBiome {
    private CrystalCliffsBiome() {}

    public static Biome createDefault() {
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();
        spawn.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 25, 1, 4));

        GenerationSettings.Builder generation = new GenerationSettings.Builder();

        BiomeEffects effects = new BiomeEffects.Builder()
            .skyColor(0x7FD0FF)
            .fogColor(0xA2C9FF)
            .waterColor(0x4CB1FF)
            .waterFogColor(0x0A1F33)
            .particleConfig(new BiomeParticleConfig(ParticleTypes.END_ROD, 0.0025F))
            .loopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
            .moodSound(BiomeMoodSound.CAVE)
            .build();

        return new Biome.Builder()
            .precipitation(false)
            .temperature(0.6F)
            .downfall(0.0F)
            .effects(effects)
            .spawnSettings(spawn.build())
            .generationSettings(generation.build())
            .build();
    }
}
