package net.liam.biomsofinfinity.datagen;

import com.google.gson.JsonArray;
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

public final class BOIWorldgenProvider implements DataProvider {
    private final FabricDataOutput output;

    public BOIWorldgenProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        futures.add(save(cachedOutput, path("worldgen/biome"), "glowshroom_forest", glowshroomBiome()));
        futures.add(save(cachedOutput, path("worldgen/biome"), "crystal_cliffs", crystalCliffsBiome()));
        futures.add(save(cachedOutput, path("worldgen/biome"), "shadow_isles", shadowIslesBiome()));

        futures.add(save(cachedOutput, path("worldgen/configured_feature"), "glowshroom_patch", glowshroomPatch()));
        futures.add(save(cachedOutput, path("worldgen/configured_feature"), "shadow_bloom_patch", shadowBloomPatch()));
        futures.add(save(cachedOutput, path("worldgen/configured_feature"), "crystalite_ore", crystaliteOre()));

        futures.add(save(cachedOutput, path("worldgen/placed_feature"), "glowshroom_patch", placedGlowshroomPatch()));
        futures.add(save(cachedOutput, path("worldgen/placed_feature"), "shadow_bloom_patch", placedShadowBloomPatch()));
        futures.add(save(cachedOutput, path("worldgen/placed_feature"), "crystalite_ore", placedCrystaliteOre()));

        futures.add(save(cachedOutput, path("worldgen/structure"), "crystal_tower", crystalTowerStructure()));
        futures.add(save(cachedOutput, path("worldgen/structure"), "lost_temple", lostTempleStructure()));

        futures.add(save(cachedOutput, path("worldgen/structure_set"), "crystal_tower", structureSet("crystal_tower", 5231872, 30, 12)));
        futures.add(save(cachedOutput, path("worldgen/structure_set"), "lost_temple", structureSet("lost_temple", 7123651, 40, 18)));

        futures.add(save(cachedOutput, path("worldgen/template_pool/crystal_tower"), "base", templatePool("crystal_tower/base", "minecraft:end_city/base_plate")));
        futures.add(save(cachedOutput, path("worldgen/template_pool/lost_temple"), "base", templatePool("lost_temple/base", "minecraft:end_city/ship")));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Bioms of Infinity worldgen";
    }

    private PackOutput.PathProvider path(String directory) {
        return output.createPathProvider(PackOutput.Target.DATA_PACK, directory);
    }

    private static JsonObject glowshroomBiome() {
        JsonObject json = baseBiome(0.8f, 0.0f, 5581584, 4465407, 4159204, 329011, 20);
        json.getAsJsonObject("effects").addProperty("grass_color", 9471030);
        json.getAsJsonObject("effects").addProperty("foliage_color", 11878480);
        json.getAsJsonObject("effects").add("particle", particle("minecraft:glow", 0.01f));
        json.getAsJsonObject("effects").addProperty("ambient_sound", "minecraft:ambient.warped_forest.loop");
        json.add("features", featureSteps(new String[][]{
            {}, {}, {}, {}, {},
            {"minecraft:ore_end_stone", "biomsofinfinity:crystalite_ore"},
            {}, {}, {"biomsofinfinity:glowshroom_patch"}, {}
        }));
        return json;
    }

    private static JsonObject crystalCliffsBiome() {
        JsonObject json = baseBiome(0.6f, 0.0f, 10682367, 8355583, 5021555, 328965, 25);
        json.getAsJsonObject("effects").add("particle", particle("minecraft:end_rod", 0.004f));
        json.getAsJsonObject("effects").addProperty("ambient_sound", "minecraft:ambient.basalt_deltas.loop");
        json.add("features", featureSteps(new String[][]{
            {}, {}, {}, {}, {},
            {"minecraft:ore_end_stone", "biomsofinfinity:crystalite_ore"},
            {}, {}, {}, {}
        }));
        return json;
    }

    private static JsonObject shadowIslesBiome() {
        JsonObject json = baseBiome(0.4f, 0.0f, 1118481, 2105376, 328965, 197379, 30);
        json.getAsJsonObject("effects").add("particle", particle("minecraft:smoke", 0.01f));
        json.getAsJsonObject("effects").addProperty("ambient_sound", "minecraft:ambient.soul_sand_valley.loop");
        json.add("features", featureSteps(new String[][]{
            {}, {}, {}, {}, {},
            {"minecraft:ore_end_stone"},
            {}, {}, {"biomsofinfinity:shadow_bloom_patch"}, {}
        }));
        return json;
    }

    private static JsonObject baseBiome(float temperature, float downfall, int fogColor, int skyColor, int waterColor, int waterFogColor, int endermanWeight) {
        JsonObject json = new JsonObject();
        json.addProperty("has_precipitation", false);
        json.addProperty("temperature", temperature);
        json.addProperty("downfall", downfall);

        JsonObject effects = new JsonObject();
        effects.addProperty("fog_color", fogColor);
        effects.addProperty("sky_color", skyColor);
        effects.addProperty("water_color", waterColor);
        effects.addProperty("water_fog_color", waterFogColor);
        json.add("effects", effects);

        JsonObject spawnSettings = new JsonObject();
        spawnSettings.addProperty("creature_spawn_probability", 0.0f);
        JsonObject spawners = new JsonObject();
        JsonArray monsters = new JsonArray();
        JsonObject enderman = new JsonObject();
        enderman.addProperty("type", "minecraft:enderman");
        enderman.addProperty("weight", endermanWeight);
        enderman.addProperty("minCount", 1);
        enderman.addProperty("maxCount", 4);
        monsters.add(enderman);
        spawners.add("monster", monsters);
        spawnSettings.add("spawners", spawners);
        json.add("spawn_settings", spawnSettings);

        JsonObject carvers = new JsonObject();
        carvers.add("air", new JsonArray());
        carvers.add("liquid", new JsonArray());
        json.add("carvers", carvers);

        return json;
    }

    private static JsonObject particle(String type, float probability) {
        JsonObject particle = new JsonObject();
        particle.addProperty("probability", probability);
        JsonObject options = new JsonObject();
        options.addProperty("type", type);
        particle.add("options", options);
        return particle;
    }

    private static JsonArray featureSteps(String[][] features) {
        JsonArray steps = new JsonArray();
        for (String[] featureList : features) {
            JsonArray step = new JsonArray();
            for (String feature : featureList) {
                step.add(feature);
            }
            steps.add(step);
        }
        return steps;
    }

    private static JsonObject glowshroomPatch() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:random_patch");
        JsonObject config = new JsonObject();
        config.addProperty("tries", 32);
        config.addProperty("xz_spread", 6);
        config.addProperty("y_spread", 3);
        JsonObject feature = new JsonObject();
        feature.addProperty("feature", "minecraft:simple_block");
        JsonObject featureConfig = new JsonObject();
        JsonObject toPlace = new JsonObject();
        toPlace.addProperty("type", "minecraft:simple_state_provider");
        JsonObject state = new JsonObject();
        state.addProperty("Name", new ResourceLocation(MOD_ID, "glowshroom_cap").toString());
        toPlace.add("state", state);
        featureConfig.add("to_place", toPlace);
        feature.add("config", featureConfig);
        config.add("feature", feature);
        json.add("config", config);
        return json;
    }

    private static JsonObject shadowBloomPatch() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:random_patch");
        JsonObject config = new JsonObject();
        config.addProperty("tries", 24);
        config.addProperty("xz_spread", 5);
        config.addProperty("y_spread", 2);
        JsonObject feature = new JsonObject();
        feature.addProperty("feature", "minecraft:simple_block");
        JsonObject featureConfig = new JsonObject();
        JsonObject toPlace = new JsonObject();
        toPlace.addProperty("type", "minecraft:simple_state_provider");
        JsonObject state = new JsonObject();
        state.addProperty("Name", new ResourceLocation(MOD_ID, "shadow_bloom").toString());
        toPlace.add("state", state);
        featureConfig.add("to_place", toPlace);
        feature.add("config", featureConfig);
        config.add("feature", feature);
        json.add("config", config);
        return json;
    }

    private static JsonObject crystaliteOre() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:ore");
        JsonObject config = new JsonObject();
        config.addProperty("size", 6);
        config.addProperty("discard_chance_on_air_exposure", 0.1);
        JsonArray targets = new JsonArray();
        JsonObject target = new JsonObject();
        JsonObject predicate = new JsonObject();
        predicate.addProperty("predicate_type", "minecraft:block_match");
        predicate.addProperty("block", "minecraft:end_stone");
        target.add("target", predicate);
        JsonObject state = new JsonObject();
        state.addProperty("Name", new ResourceLocation(MOD_ID, "crystalite_ore").toString());
        target.add("state", state);
        targets.add(target);
        config.add("targets", targets);
        json.add("config", config);
        return json;
    }

    private static JsonObject placedGlowshroomPatch() {
        return simplePlaced("biomsofinfinity:glowshroom_patch", 6, true, "MOTION_BLOCKING");
    }

    private static JsonObject placedShadowBloomPatch() {
        return simplePlaced("biomsofinfinity:shadow_bloom_patch", 4, true, "MOTION_BLOCKING");
    }

    private static JsonObject placedCrystaliteOre() {
        JsonObject json = new JsonObject();
        json.addProperty("feature", new ResourceLocation(MOD_ID, "crystalite_ore").toString());
        JsonArray placement = new JsonArray();
        placement.add(count(8));
        placement.add(inSquare());
        placement.add(heightRange(0, 80));
        placement.add(biomeFilter());
        json.add("placement", placement);
        return json;
    }

    private static JsonObject simplePlaced(String featureId, int count, boolean heightmap, String heightmapName) {
        JsonObject json = new JsonObject();
        json.addProperty("feature", featureId);
        JsonArray placement = new JsonArray();
        placement.add(count(count));
        placement.add(inSquare());
        if (heightmap) {
            JsonObject modifier = new JsonObject();
            modifier.addProperty("type", "minecraft:heightmap");
            modifier.addProperty("heightmap", heightmapName);
            placement.add(modifier);
        }
        placement.add(biomeFilter());
        json.add("placement", placement);
        return json;
    }

    private static JsonObject count(int value) {
        JsonObject modifier = new JsonObject();
        modifier.addProperty("type", "minecraft:count");
        modifier.addProperty("count", value);
        return modifier;
    }

    private static JsonObject inSquare() {
        JsonObject modifier = new JsonObject();
        modifier.addProperty("type", "minecraft:in_square");
        return modifier;
    }

    private static JsonObject heightRange(int min, int max) {
        JsonObject modifier = new JsonObject();
        modifier.addProperty("type", "minecraft:height_range");
        JsonObject height = new JsonObject();
        height.addProperty("type", "minecraft:uniform");
        JsonObject minObj = new JsonObject();
        minObj.addProperty("absolute", min);
        JsonObject maxObj = new JsonObject();
        maxObj.addProperty("absolute", max);
        height.add("min_inclusive", minObj);
        height.add("max_inclusive", maxObj);
        modifier.add("height", height);
        return modifier;
    }

    private static JsonObject biomeFilter() {
        JsonObject modifier = new JsonObject();
        modifier.addProperty("type", "minecraft:biome");
        return modifier;
    }

    private static JsonObject crystalTowerStructure() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:jigsaw");
        json.add("biomes", stringArray(new ResourceLocation(MOD_ID, "crystal_cliffs").toString()));
        json.add("spawn_overrides", new JsonObject());
        json.addProperty("step", "surface_structures");
        json.addProperty("terrain_adaptation", "beard_thin");
        json.add("start_height", absoluteHeight(70));
        json.addProperty("project_start_to_heightmap", "WORLD_SURFACE_WG");
        json.addProperty("start_pool", new ResourceLocation(MOD_ID, "crystal_tower/base").toString());
        json.addProperty("size", 2);
        json.addProperty("max_distance_from_center", 64);
        json.addProperty("use_expansion_hack", false);
        return json;
    }

    private static JsonObject lostTempleStructure() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:jigsaw");
        json.add("biomes", stringArray(new ResourceLocation(MOD_ID, "shadow_isles").toString()));
        json.add("spawn_overrides", new JsonObject());
        json.addProperty("step", "surface_structures");
        json.addProperty("terrain_adaptation", "beard_thin");
        json.add("start_height", absoluteHeight(65));
        json.addProperty("project_start_to_heightmap", "WORLD_SURFACE_WG");
        json.addProperty("start_pool", new ResourceLocation(MOD_ID, "lost_temple/base").toString());
        json.addProperty("size", 1);
        json.addProperty("max_distance_from_center", 48);
        json.addProperty("use_expansion_hack", false);
        return json;
    }

    private static JsonArray stringArray(String... values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(value);
        }
        return array;
    }

    private static JsonObject absoluteHeight(int value) {
        JsonObject height = new JsonObject();
        height.addProperty("absolute", value);
        return height;
    }

    private static JsonObject structureSet(String structureId, int salt, int spacing, int separation) {
        JsonObject json = new JsonObject();
        JsonArray structures = new JsonArray();
        JsonObject entry = new JsonObject();
        entry.addProperty("structure", new ResourceLocation(MOD_ID, structureId).toString());
        entry.addProperty("weight", 1);
        structures.add(entry);
        json.add("structures", structures);
        JsonObject placement = new JsonObject();
        placement.addProperty("type", "minecraft:random_spread");
        placement.addProperty("salt", salt);
        placement.addProperty("spacing", spacing);
        placement.addProperty("separation", separation);
        json.add("placement", placement);
        return json;
    }

    private static JsonObject templatePool(String name, String element) {
        JsonObject json = new JsonObject();
        json.addProperty("name", new ResourceLocation(MOD_ID, name).toString());
        json.addProperty("fallback", "minecraft:empty");
        JsonArray elements = new JsonArray();
        JsonObject weighted = new JsonObject();
        JsonObject elementObj = new JsonObject();
        elementObj.addProperty("location", element);
        elementObj.addProperty("processors", "minecraft:empty");
        elementObj.addProperty("projection", "rigid");
        weighted.add("element", elementObj);
        weighted.addProperty("weight", 1);
        elements.add(weighted);
        json.add("elements", elements);
        return json;
    }

    private CompletableFuture<?> save(CachedOutput output, PackOutput.PathProvider provider, String name, JsonObject json) {
        Path path = provider.json(new ResourceLocation(MOD_ID, name));
        return DataProvider.saveStable(output, json, path);
    }
}
