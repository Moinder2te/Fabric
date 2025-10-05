package net.liam.biomsofinfinity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.liam.biomsofinfinity.registry.ModBlocks;
import net.liam.biomsofinfinity.registry.ModItems;

public class BOILangProvider extends FabricLanguageProvider {
    public BOILangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    protected void generateTranslations(TranslationBuilder builder) {
        builder.add("itemGroup.biomsofinfinity.general", "Bioms of Infinity");

        builder.add(ModBlocks.GLOWSHROOM_CAP, "Glowshroom Cap");
        builder.add(ModBlocks.GLOWSHROOM_STEM, "Glowshroom Stem");
        builder.add(ModBlocks.LUMINESCENT_MOSS, "Luminescent Moss");
        builder.add(ModBlocks.CRYSTALITE_BLOCK, "Crystalite Block");
        builder.add(ModBlocks.CRYSTALITE_CLUSTER, "Crystalite Cluster");
        builder.add(ModBlocks.CRYSTALITE_ORE, "Crystalite Ore");
        builder.add(ModBlocks.SHADOW_SOIL, "Shadow Soil");
        builder.add(ModBlocks.SHADOW_BLOOM, "Shadow Bloom");
        builder.add(ModBlocks.SHADOW_LANTERN, "Shadow Lantern");
        builder.add(ModBlocks.SHADOW_ALTAR, "Shadow Altar");
        builder.add(ModBlocks.INFUSION_TABLE, "Infusion Table");
        builder.add(ModBlocks.SOUL_FORGE, "Soul Forge");
        builder.add(ModBlocks.LUMINA_WORKBENCH, "Lumina Workbench");

        builder.add(ModItems.GLOWSHROOM_SPORE, "Glowshroom Spore");
        builder.add(ModItems.LUMINOUS_FILAMENT, "Luminous Filament");
        builder.add(ModItems.CRYSTALITE_SHARD, "Crystalite Shard");
        builder.add(ModItems.SHADOW_ESSENCE, "Shadow Essence");
        builder.add(ModItems.SHADOW_HEART, "Shadow Heart");
        builder.add(ModItems.SHADOWSTEEL_INGOT, "Shadowsteel Ingot");
        builder.add(ModItems.AETHERIUM_DUST, "Aetherium Dust");
        builder.add(ModItems.AURORITE_GEM, "Aurorite Gem");
        builder.add(ModItems.SOULGLASS_FRAGMENT, "Soulglass Fragment");
        builder.add(ModItems.LUMINOUS_CRYSTAL, "Luminous Crystal");
        builder.add(ModItems.CRYSTALITE_SWORD, "Crystalite Sword");
        builder.add(ModItems.SHADOWSTEEL_CHESTPLATE, "Shadowsteel Chestplate");

        builder.add("biome.biomsofinfinity.glowshroom_forest", "Glowshroom Forest");
        builder.add("biome.biomsofinfinity.crystal_cliffs", "Crystal Cliffs");
        builder.add("biome.biomsofinfinity.shadow_isles", "Shadow Isles");
        builder.add("boss.biomsofinfinity.shadow_king", "Shadow King of the Isles");

        builder.add("tooltip.biomsofinfinity.shadow_heart", "Use on a Shadow Altar within the Shadow Isles to summon the Shadow King.");
        builder.add("tooltip.biomsofinfinity.crystalite_sword", "Marks foes with radiant light on hit.");
        builder.add("tooltip.biomsofinfinity.shadowsteel_chestplate", "Grants temporary resistance while worn.");

        builder.add("message.biomsofinfinity.shadow_altar.need_heart", "The altar remains dormant without a Shadow Heart.");
        builder.add("message.biomsofinfinity.shadow_altar.wrong_biome", "The altar rejects your offering outside the Shadow Isles.");
        builder.add("message.biomsofinfinity.shadow_altar.on_cooldown", "The altar needs %s more seconds to recover.");
        builder.add("message.biomsofinfinity.shadow_altar.spawn", "The shadows stir as the king awakens.");
        builder.add("message.biomsofinfinity.boss.disabled", "Boss summoning is disabled by configuration.");
    }
}
