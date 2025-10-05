package net.liam.biomsofinfinity.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.block.entity.InfusionTableBlockEntity;
import net.liam.biomsofinfinity.block.entity.LuminaWorkbenchBlockEntity;
import net.liam.biomsofinfinity.block.entity.ShadowAltarBlockEntity;
import net.liam.biomsofinfinity.block.entity.SoulForgeBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlockEntities {
    public static final BlockEntityType<ShadowAltarBlockEntity> SHADOW_ALTAR = register("shadow_altar", FabricBlockEntityTypeBuilder.create(ShadowAltarBlockEntity::new, ModBlocks.SHADOW_ALTAR).build());
    public static final BlockEntityType<InfusionTableBlockEntity> INFUSION_TABLE = register("infusion_table", FabricBlockEntityTypeBuilder.create(InfusionTableBlockEntity::new, ModBlocks.INFUSION_TABLE).build());
    public static final BlockEntityType<SoulForgeBlockEntity> SOUL_FORGE = register("soul_forge", FabricBlockEntityTypeBuilder.create(SoulForgeBlockEntity::new, ModBlocks.SOUL_FORGE).build());
    public static final BlockEntityType<LuminaWorkbenchBlockEntity> LUMINA_WORKBENCH = register("lumina_workbench", FabricBlockEntityTypeBuilder.create(LuminaWorkbenchBlockEntity::new, ModBlocks.LUMINA_WORKBENCH).build());

    private ModBlockEntities() {}

    private static <T extends net.minecraft.block.entity.BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        Identifier id = BiomsOfInfinityMod.id(name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, type);
    }

    public static void init() {
        // Trigger static initialisation
    }
}
