package net.liam.biomsofinfinity.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.liam.biomsofinfinity.BiomsOfInfinityMod.MOD_ID;

/**
 * Generates the blockstate and model JSON definitions used by the mod. The
 * outputs intentionally point at vanilla textures so that artists can replace
 * them later without causing missing-model errors during development.
 */
public final class BOIAssetProvider implements DataProvider {
    private final FabricDataOutput output;

    public BOIAssetProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        PackOutput.PathProvider blockStates = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        futures.add(singleVariant(blockStates, cachedOutput, "glowshroom_cap", "biomsofinfinity:block/glowshroom_cap"));
        futures.add(singleVariant(blockStates, cachedOutput, "glowshroom_stem", "biomsofinfinity:block/glowshroom_stem"));
        futures.add(singleVariant(blockStates, cachedOutput, "luminescent_moss", "biomsofinfinity:block/luminescent_moss"));
        futures.add(singleVariant(blockStates, cachedOutput, "crystalite_block", "biomsofinfinity:block/crystalite_block"));
        futures.add(singleVariant(blockStates, cachedOutput, "crystalite_cluster", "biomsofinfinity:block/crystalite_cluster"));
        futures.add(singleVariant(blockStates, cachedOutput, "crystalite_ore", "biomsofinfinity:block/crystalite_ore"));
        futures.add(singleVariant(blockStates, cachedOutput, "shadow_soil", "biomsofinfinity:block/shadow_soil"));
        futures.add(singleVariant(blockStates, cachedOutput, "shadow_bloom", "biomsofinfinity:block/shadow_bloom"));
        futures.add(lanternState(blockStates, cachedOutput));
        futures.add(altarState(blockStates, cachedOutput));
        futures.add(singleVariant(blockStates, cachedOutput, "infusion_table", "biomsofinfinity:block/infusion_table"));
        futures.add(singleVariant(blockStates, cachedOutput, "soul_forge", "biomsofinfinity:block/soul_forge"));
        futures.add(singleVariant(blockStates, cachedOutput, "lumina_workbench", "biomsofinfinity:block/lumina_workbench"));

        PackOutput.PathProvider blockModels = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/block");
        futures.add(save(blockModels, cachedOutput, "glowshroom_cap", cubeAll("minecraft:block/shroomlight")));
        futures.add(save(blockModels, cachedOutput, "glowshroom_stem", cubeAll("minecraft:block/mushroom_stem")));
        futures.add(save(blockModels, cachedOutput, "luminescent_moss", cubeAll("minecraft:block/moss_block")));
        futures.add(save(blockModels, cachedOutput, "crystalite_block", cubeAll("minecraft:block/amethyst_block")));
        futures.add(save(blockModels, cachedOutput, "crystalite_cluster", cubeAll("minecraft:block/large_amethyst_bud")));
        futures.add(save(blockModels, cachedOutput, "crystalite_ore", cubeAll("minecraft:block/end_stone")));
        futures.add(save(blockModels, cachedOutput, "shadow_soil", cubeAll("minecraft:block/mud")));
        futures.add(save(blockModels, cachedOutput, "shadow_bloom", cross("minecraft:block/wither_rose")));
        futures.add(save(blockModels, cachedOutput, "shadow_lantern", lantern("minecraft:block/soul_lantern", false)));
        futures.add(save(blockModels, cachedOutput, "shadow_lantern_hanging", lantern("minecraft:block/soul_lantern", true)));
        futures.add(save(blockModels, cachedOutput, "shadow_altar", cubeAll("minecraft:block/obsidian")));
        futures.add(save(blockModels, cachedOutput, "infusion_table", parentOnly("minecraft:block/enchanting_table")));
        futures.add(save(blockModels, cachedOutput, "soul_forge", cubeAll("minecraft:block/blast_furnace_front_on")));
        futures.add(save(blockModels, cachedOutput, "lumina_workbench", cubeAll("minecraft:block/crafting_table_top")));

        PackOutput.PathProvider itemModels = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
        // Block items
        futures.add(parentedItem(itemModels, cachedOutput, "glowshroom_cap", "biomsofinfinity:block/glowshroom_cap"));
        futures.add(parentedItem(itemModels, cachedOutput, "glowshroom_stem", "biomsofinfinity:block/glowshroom_stem"));
        futures.add(parentedItem(itemModels, cachedOutput, "luminescent_moss", "biomsofinfinity:block/luminescent_moss"));
        futures.add(parentedItem(itemModels, cachedOutput, "crystalite_block", "biomsofinfinity:block/crystalite_block"));
        futures.add(parentedItem(itemModels, cachedOutput, "crystalite_cluster", "biomsofinfinity:block/crystalite_cluster"));
        futures.add(parentedItem(itemModels, cachedOutput, "crystalite_ore", "biomsofinfinity:block/crystalite_ore"));
        futures.add(parentedItem(itemModels, cachedOutput, "shadow_soil", "biomsofinfinity:block/shadow_soil"));
        futures.add(parentedItem(itemModels, cachedOutput, "shadow_bloom", "biomsofinfinity:block/shadow_bloom"));
        futures.add(parentedItem(itemModels, cachedOutput, "shadow_lantern", "biomsofinfinity:block/shadow_lantern"));
        futures.add(parentedItem(itemModels, cachedOutput, "shadow_altar", "biomsofinfinity:block/shadow_altar"));
        futures.add(parentedItem(itemModels, cachedOutput, "infusion_table", "biomsofinfinity:block/infusion_table"));
        futures.add(parentedItem(itemModels, cachedOutput, "soul_forge", "biomsofinfinity:block/soul_forge"));
        futures.add(parentedItem(itemModels, cachedOutput, "lumina_workbench", "biomsofinfinity:block/lumina_workbench"));

        // Simple generated items referencing vanilla placeholders
        futures.add(generatedItem(itemModels, cachedOutput, "glowshroom_spore", "minecraft:item/chorus_fruit"));
        futures.add(generatedItem(itemModels, cachedOutput, "luminous_filament", "minecraft:item/glowstone_dust"));
        futures.add(generatedItem(itemModels, cachedOutput, "crystalite_shard", "minecraft:item/amethyst_shard"));
        futures.add(generatedItem(itemModels, cachedOutput, "shadow_essence", "minecraft:item/ink_sac"));
        futures.add(generatedItem(itemModels, cachedOutput, "shadow_heart", "minecraft:item/ender_pearl"));
        futures.add(generatedItem(itemModels, cachedOutput, "shadowsteel_ingot", "minecraft:item/netherite_ingot"));
        futures.add(generatedItem(itemModels, cachedOutput, "aetherium_dust", "minecraft:item/ghast_tear"));
        futures.add(generatedItem(itemModels, cachedOutput, "aurorite_gem", "minecraft:item/diamond"));
        futures.add(generatedItem(itemModels, cachedOutput, "soulglass_fragment", "minecraft:item/prismarine_shard"));
        futures.add(generatedItem(itemModels, cachedOutput, "luminous_crystal", "minecraft:item/experience_bottle"));
        futures.add(generatedItem(itemModels, cachedOutput, "shadowsteel_chestplate", "minecraft:item/netherite_chestplate"));
        futures.add(generatedItem(itemModels, cachedOutput, "crystalite_sword", "minecraft:item/diamond_sword"));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Bioms of Infinity assets";
    }

    private static CompletableFuture<?> singleVariant(PackOutput.PathProvider provider, CachedOutput output, String name, String modelId) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        variants.add("", model(modelId));
        root.add("variants", variants);
        return save(output, provider.json(id(name)), root);
    }

    private static CompletableFuture<?> lanternState(PackOutput.PathProvider provider, CachedOutput output) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        variants.add("hanging=false", model("biomsofinfinity:block/shadow_lantern"));
        variants.add("hanging=true", model("biomsofinfinity:block/shadow_lantern_hanging"));
        root.add("variants", variants);
        return save(output, provider.json(id("shadow_lantern")), root);
    }

    private static CompletableFuture<?> altarState(PackOutput.PathProvider provider, CachedOutput output) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        variants.add("lit=false", model("biomsofinfinity:block/shadow_altar"));
        variants.add("lit=true", model("biomsofinfinity:block/shadow_altar"));
        root.add("variants", variants);
        return save(output, provider.json(id("shadow_altar")), root);
    }

    private static CompletableFuture<?> parentedItem(PackOutput.PathProvider provider, CachedOutput output, String name, String parent) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        return save(output, provider.json(id(name)), json);
    }

    private static CompletableFuture<?> generatedItem(PackOutput.PathProvider provider, CachedOutput output, String name, String texture) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", texture);
        json.add("textures", textures);
        return save(output, provider.json(id(name)), json);
    }

    private static CompletableFuture<?> save(PackOutput.PathProvider provider, CachedOutput output, String name, JsonObject json) {
        return save(output, provider.json(id(name)), json);
    }

    private static CompletableFuture<?> save(CachedOutput output, Path path, JsonObject json) {
        return DataProvider.saveStable(output, json, path);
    }

    private static JsonObject cubeAll(String texture) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cube_all");
        JsonObject textures = new JsonObject();
        textures.addProperty("all", texture);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject cross(String texture) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:block/cross");
        JsonObject textures = new JsonObject();
        textures.addProperty("cross", texture);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject lantern(String texture, boolean hanging) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", hanging ? "minecraft:block/template_lantern_hanging" : "minecraft:block/template_lantern");
        JsonObject textures = new JsonObject();
        textures.addProperty("lantern", texture);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject parentOnly(String parent) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        return json;
    }

    private static JsonObject model(String modelId) {
        JsonObject json = new JsonObject();
        json.addProperty("model", modelId);
        return json;
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
