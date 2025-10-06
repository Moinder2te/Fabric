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

public final class BOIRecipeProvider implements DataProvider {
    private final FabricDataOutput output;

    public BOIRecipeProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");

        futures.add(save(cachedOutput, provider, "crystalite_block", shaped(new String[]{"CC", "CC"}, new char[]{'C'}, new String[]{"crystalite_shard"}, "building", "crystalite_block", 1)));
        futures.add(save(cachedOutput, provider, "crystalite_shard_from_block", shapeless(new String[]{"crystalite_block"}, "misc", "crystalite_shard", 4)));
        futures.add(save(cachedOutput, provider, "crystalite_sword", shaped(new String[]{" C ", " C ", " S "}, new char[]{'C', 'S'}, new String[]{"crystalite_shard", "luminous_filament"}, "combat", "crystalite_sword", 1)));
        futures.add(save(cachedOutput, provider, "luminous_filament", shapeless(new String[]{"glowshroom_spore", "luminous_crystal"}, "misc", "luminous_filament", 2)));
        futures.add(save(cachedOutput, provider, "shadowsteel_chestplate", shaped(new String[]{"S S", "SSS", "SSS"}, new char[]{'S'}, new String[]{"shadowsteel_ingot"}, "combat", "shadowsteel_chestplate", 1)));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Bioms of Infinity recipes";
    }

    private static JsonObject shaped(String[] pattern, char[] symbols, String[] items, String category, String resultItem, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        json.addProperty("category", category);

        JsonArray patternArray = new JsonArray();
        for (String row : pattern) {
            patternArray.add(row);
        }
        json.add("pattern", patternArray);

        JsonObject key = new JsonObject();
        for (int i = 0; i < symbols.length; i++) {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", new ResourceLocation(MOD_ID, items[i]).toString());
            key.add(String.valueOf(symbols[i]), ingredient);
        }
        json.add("key", key);

        json.add("result", resultObject(resultItem, count));
        return json;
    }

    private static JsonObject shapeless(String[] ingredients, String category, String resultItem, int count) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shapeless");
        json.addProperty("category", category);

        JsonArray ingredientArray = new JsonArray();
        for (String ingredient : ingredients) {
            JsonObject entry = new JsonObject();
            entry.addProperty("item", new ResourceLocation(MOD_ID, ingredient).toString());
            ingredientArray.add(entry);
        }
        json.add("ingredients", ingredientArray);
        json.add("result", resultObject(resultItem, count));
        return json;
    }

    private static JsonObject resultObject(String item, int count) {
        JsonObject result = new JsonObject();
        result.addProperty("item", new ResourceLocation(MOD_ID, item).toString());
        if (count > 1) {
            result.addProperty("count", count);
        }
        return result;
    }

    private static CompletableFuture<?> save(CachedOutput output, PackOutput.PathProvider provider, String name, JsonObject json) {
        Path path = provider.json(new ResourceLocation(MOD_ID, name));
        return DataProvider.saveStable(output, json, path);
    }
}
