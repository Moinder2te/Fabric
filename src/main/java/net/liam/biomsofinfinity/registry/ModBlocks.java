package net.liam.biomsofinfinity.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.block.InfusionTableBlock;
import net.liam.biomsofinfinity.block.LuminaWorkbenchBlock;
import net.liam.biomsofinfinity.block.ShadowAltarBlock;
import net.liam.biomsofinfinity.block.SoulForgeBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModBlocks {
    private static final Map<Identifier, Block> REGISTERED_BLOCKS = new LinkedHashMap<>();

    public static final Block GLOWSHROOM_CAP = register("glowshroom_cap", new Block(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).luminance(12).mapColor(MapColor.MAGENTA))); 
    public static final Block GLOWSHROOM_STEM = register("glowshroom_stem", new PillarBlock(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).luminance(6)));
    public static final Block LUMINESCENT_MOSS = register("luminescent_moss", new Block(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).luminance(10)));

    public static final Block CRYSTALITE_BLOCK = register("crystalite_block", new Block(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).luminance(8)));
    public static final Block CRYSTALITE_CLUSTER = register("crystalite_cluster", new AmethystClusterBlock(5, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).luminance(14)));
    public static final Block CRYSTALITE_ORE = register("crystalite_ore", new Block(FabricBlockSettings.copyOf(Blocks.END_STONE).strength(4.0F, 10.0F).luminance(4)));

    public static final Block SHADOW_SOIL = register("shadow_soil", new Block(FabricBlockSettings.copyOf(Blocks.END_STONE).mapColor(MapColor.BLACK).strength(2.5F).luminance(2)));
    public static final Block SHADOW_BLOOM = register("shadow_bloom", new FlowerBlock(StatusEffects.WEAKNESS, 120, FabricBlockSettings.copyOf(Blocks.WITHER_ROSE).luminance(7)));
    public static final Block SHADOW_LANTERN = register("shadow_lantern", new LanternBlock(FabricBlockSettings.copyOf(Blocks.SOUL_LANTERN).luminance(15)));

    public static final Block SHADOW_ALTAR = register("shadow_altar", new ShadowAltarBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).luminance(6).strength(6.0F).nonOpaque()));
    public static final Block INFUSION_TABLE = register("infusion_table", new InfusionTableBlock(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE)));
    public static final Block SOUL_FORGE = register("soul_forge", new SoulForgeBlock(FabricBlockSettings.copyOf(Blocks.BLAST_FURNACE).luminance(10)));
    public static final Block LUMINA_WORKBENCH = register("lumina_workbench", new LuminaWorkbenchBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE).luminance(8)));

    private ModBlocks() {}

    private static Block register(String name, Block block) {
        Identifier id = BiomsOfInfinityMod.id(name);
        Registry.register(Registries.BLOCK, id, block);
        REGISTERED_BLOCKS.put(id, block);
        if (BiomsOfInfinityMod.getConfig().debug.logAllRegistrations) {
            BiomsOfInfinityMod.LOGGER.info("Registered block {}", id);
        }
        return block;
    }

    public static void init() {
        // Class loading triggers static initialisation
    }

    public static int getRegisteredBlockCount() {
        return REGISTERED_BLOCKS.size();
    }
}
