package net.liam.biomsofinfinity.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModParticles {
    public static final DefaultParticleType SHADOW_SPARK = register("shadow_spark");
    public static final DefaultParticleType CRYSTAL_GLEAM = register("crystal_gleam");

    private ModParticles() {}

    private static DefaultParticleType register(String name) {
        Identifier id = BiomsOfInfinityMod.id(name);
        DefaultParticleType type = FabricParticleTypes.simple();
        return Registry.register(Registries.PARTICLE_TYPE, id, type);
    }

    public static void init() {
        // load class
    }
}
