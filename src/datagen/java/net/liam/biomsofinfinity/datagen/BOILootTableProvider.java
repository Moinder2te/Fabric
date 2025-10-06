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

public final class BOILootTableProvider implements DataProvider {
    private final FabricDataOutput output;

    public BOILootTableProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.DATA_PACK, "loot_tables/blocks");

        futures.add(save(cachedOutput, provider, "crystalite_block", simpleDrop("crystalite_block")));
        futures.add(save(cachedOutput, provider, "crystalite_cluster", simpleDrop("crystalite_cluster")));
        futures.add(save(cachedOutput, provider, "crystalite_ore", oreDrop("crystalite_shard")));
        futures.add(save(cachedOutput, provider, "glowshroom_cap", simpleDrop("glowshroom_cap")));
        futures.add(save(cachedOutput, provider, "glowshroom_stem", simpleDrop("glowshroom_stem")));
        futures.add(save(cachedOutput, provider, "infusion_table", simpleDrop("infusion_table")));
        futures.add(save(cachedOutput, provider, "lumina_workbench", simpleDrop("lumina_workbench")));
        futures.add(save(cachedOutput, provider, "luminescent_moss", simpleDrop("luminescent_moss")));
        futures.add(save(cachedOutput, provider, "shadow_altar", simpleDrop("shadow_altar")));
        futures.add(save(cachedOutput, provider, "shadow_bloom", itemDrop("shadow_essence")));
        futures.add(save(cachedOutput, provider, "shadow_lantern", simpleDrop("shadow_lantern")));
        futures.add(save(cachedOutput, provider, "shadow_soil", simpleDrop("shadow_soil")));
        futures.add(save(cachedOutput, provider, "soul_forge", simpleDrop("soul_forge")));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Bioms of Infinity block loot tables";
    }

    private static JsonObject simpleDrop(String itemId) {
        JsonObject pool = lootPool();
        pool.add("entries", singletonEntry(itemId));
        pool.add("conditions", survivesExplosion());
        JsonObject table = new JsonObject();
        table.addProperty("type", "minecraft:block");
        JsonArray pools = new JsonArray();
        pools.add(pool);
        table.add("pools", pools);
        return table;
    }

    private static JsonObject itemDrop(String itemId) {
        JsonObject pool = lootPool();
        pool.add("entries", singletonEntry(itemId));
        JsonObject table = new JsonObject();
        table.addProperty("type", "minecraft:block");
        JsonArray pools = new JsonArray();
        pools.add(pool);
        table.add("pools", pools);
        return table;
    }

    private static JsonObject oreDrop(String itemId) {
        JsonObject entry = entry(itemId);
        JsonArray functions = new JsonArray();
        JsonObject setCount = new JsonObject();
        setCount.addProperty("function", "minecraft:set_count");
        JsonObject count = new JsonObject();
        count.addProperty("type", "minecraft:uniform");
        count.addProperty("min", 1.0);
        count.addProperty("max", 2.0);
        setCount.add("count", count);
        functions.add(setCount);
        JsonObject fortune = new JsonObject();
        fortune.addProperty("function", "minecraft:apply_bonus");
        fortune.addProperty("enchantment", "minecraft:fortune");
        fortune.addProperty("formula", "minecraft:ore_drops");
        functions.add(fortune);
        entry.add("functions", functions);

        JsonObject pool = lootPool();
        JsonArray entries = new JsonArray();
        entries.add(entry);
        pool.add("entries", entries);
        pool.add("conditions", survivesExplosion());

        JsonObject table = new JsonObject();
        table.addProperty("type", "minecraft:block");
        JsonArray pools = new JsonArray();
        pools.add(pool);
        table.add("pools", pools);
        return table;
    }

    private static JsonObject lootPool() {
        JsonObject pool = new JsonObject();
        pool.addProperty("rolls", 1);
        return pool;
    }

    private static JsonArray singletonEntry(String itemId) {
        JsonArray entries = new JsonArray();
        entries.add(entry(itemId));
        return entries;
    }

    private static JsonObject entry(String itemId) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", new ResourceLocation(MOD_ID, itemId).toString());
        return entry;
    }

    private static JsonArray survivesExplosion() {
        JsonArray conditions = new JsonArray();
        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:survives_explosion");
        conditions.add(condition);
        return conditions;
    }

    private CompletableFuture<?> save(CachedOutput output, PackOutput.PathProvider provider, String name, JsonObject json) {
        Path path = provider.json(new ResourceLocation(MOD_ID, name));
        return DataProvider.saveStable(output, json, path);
    }
}
