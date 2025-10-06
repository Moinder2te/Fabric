package net.liam.biomsofinfinity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.liam.biomsofinfinity.registry.ModBlocks;
import net.liam.biomsofinfinity.registry.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class BOIModelProvider extends FabricModelProvider {
    public BOIModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GLOWSHROOM_CAP);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GLOWSHROOM_STEM);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LUMINESCENT_MOSS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRYSTALITE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRYSTALITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRYSTALITE_CLUSTER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHADOW_SOIL);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHADOW_BLOOM);
        blockStateModelGenerator.registerLantern(ModBlocks.SHADOW_LANTERN);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SHADOW_ALTAR);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.INFUSION_TABLE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUL_FORGE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LUMINA_WORKBENCH);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.GLOWSHROOM_SPORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LUMINOUS_FILAMENT, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRYSTALITE_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHADOW_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHADOW_HEART, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHADOWSTEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.AETHERIUM_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.AURORITE_GEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOULGLASS_FRAGMENT, Models.GENERATED);
        itemModelGenerator.register(ModItems.LUMINOUS_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRYSTALITE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SHADOWSTEEL_CHESTPLATE, Models.GENERATED);
    }
}
