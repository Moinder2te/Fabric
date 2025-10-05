package net.liam.biomsofinfinity.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.item.CrystaliteSwordItem;
import net.liam.biomsofinfinity.item.ShadowHeartItem;
import net.liam.biomsofinfinity.item.ShadowsteelChestplateItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModItems {
    private static final Map<Identifier, Item> REGISTERED_ITEMS = new LinkedHashMap<>();

    public static final Item GLOWSHROOM_SPORE = register("glowshroom_spore", new Item(settings()));
    public static final Item LUMINOUS_FILAMENT = register("luminous_filament", new Item(settings()));
    public static final Item CRYSTALITE_SHARD = register("crystalite_shard", new Item(settings()));
    public static final Item SHADOW_ESSENCE = register("shadow_essence", new Item(settings()));
    public static final Item SHADOW_HEART = register("shadow_heart", new ShadowHeartItem(settings().maxCount(1)));
    public static final Item SHADOWSTEEL_INGOT = register("shadowsteel_ingot", new Item(settings()));
    public static final Item AETHERIUM_DUST = register("aetherium_dust", new Item(settings()));
    public static final Item AURORITE_GEM = register("aurorite_gem", new Item(settings()));
    public static final Item SOULGLASS_FRAGMENT = register("soulglass_fragment", new Item(settings()));
    public static final Item LUMINOUS_CRYSTAL = register("luminous_crystal", new Item(settings()));

    public static final Item CRYSTALITE_SWORD = register("crystalite_sword", new CrystaliteSwordItem(settings().maxCount(1)));
    public static final Item SHADOWSTEEL_CHESTPLATE = register("shadowsteel_chestplate", new ShadowsteelChestplateItem(settings().maxCount(1)));

    public static final Item GLOWSHROOM_CAP_ITEM = registerBlockItem("glowshroom_cap", ModBlocks.GLOWSHROOM_CAP);
    public static final Item GLOWSHROOM_STEM_ITEM = registerBlockItem("glowshroom_stem", ModBlocks.GLOWSHROOM_STEM);
    public static final Item LUMINESCENT_MOSS_ITEM = registerBlockItem("luminescent_moss", ModBlocks.LUMINESCENT_MOSS);
    public static final Item CRYSTALITE_BLOCK_ITEM = registerBlockItem("crystalite_block", ModBlocks.CRYSTALITE_BLOCK);
    public static final Item CRYSTALITE_CLUSTER_ITEM = registerBlockItem("crystalite_cluster", ModBlocks.CRYSTALITE_CLUSTER);
    public static final Item CRYSTALITE_ORE_ITEM = registerBlockItem("crystalite_ore", ModBlocks.CRYSTALITE_ORE);
    public static final Item SHADOW_SOIL_ITEM = registerBlockItem("shadow_soil", ModBlocks.SHADOW_SOIL);
    public static final Item SHADOW_BLOOM_ITEM = registerBlockItem("shadow_bloom", ModBlocks.SHADOW_BLOOM);
    public static final Item SHADOW_LANTERN_ITEM = registerBlockItem("shadow_lantern", ModBlocks.SHADOW_LANTERN);
    public static final Item SHADOW_ALTAR_ITEM = registerBlockItem("shadow_altar", ModBlocks.SHADOW_ALTAR);
    public static final Item INFUSION_TABLE_ITEM = registerBlockItem("infusion_table", ModBlocks.INFUSION_TABLE);
    public static final Item SOUL_FORGE_ITEM = registerBlockItem("soul_forge", ModBlocks.SOUL_FORGE);
    public static final Item LUMINA_WORKBENCH_ITEM = registerBlockItem("lumina_workbench", ModBlocks.LUMINA_WORKBENCH);

    private static ItemGroup ITEM_GROUP;

    private ModItems() {}

    private static Item register(String name, Item item) {
        Identifier id = BiomsOfInfinityMod.id(name);
        Registry.register(Registries.ITEM, id, item);
        REGISTERED_ITEMS.put(id, item);
        return item;
    }

    private static Item registerBlockItem(String name, Block block) {
        return register(name, new BlockItem(block, settings()));
    }

    private static FabricItemSettings settings() {
        return new FabricItemSettings();
    }

    public static void init() {
        if (ITEM_GROUP == null) {
            ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, BiomsOfInfinityMod.id("general"), FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.SHADOW_HEART))
                .displayName(Text.translatable("itemGroup.biomsofinfinity.general"))
                .entries((displayContext, entries) -> REGISTERED_ITEMS.values().forEach(entries::add))
                .build());
        }
    }

    public static int getRegisteredItemCount() {
        return REGISTERED_ITEMS.size();
    }
}
