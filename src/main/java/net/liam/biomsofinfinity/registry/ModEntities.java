package net.liam.biomsofinfinity.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.content.boss.ShadowKingEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModEntities {
    private static final Map<Identifier, EntityType<?>> BOSSES = new LinkedHashMap<>();

    public static final EntityType<ShadowKingEntity> SHADOW_KING = registerBoss("shadow_king",
        FabricEntityTypeBuilder.<ShadowKingEntity>create(SpawnGroup.MONSTER, ShadowKingEntity::new)
            .dimensions(EntityDimensions.fixed(0.9F, 3.2F))
            .fireImmune()
            .trackRangeBlocks(64)
            .forceTrackedVelocityUpdates(true)
            .build());

    private ModEntities() {}

    private static <T extends net.minecraft.entity.mob.HostileEntity> EntityType<T> registerBoss(String name, EntityType<T> type) {
        Identifier id = BiomsOfInfinityMod.id(name);
        Registry.register(Registries.ENTITY_TYPE, id, type);
        BOSSES.put(id, type);
        return type;
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(SHADOW_KING, ShadowKingEntity.createAttributes());
    }

    public static int getRegisteredBossCount() {
        return BOSSES.size();
    }
}
