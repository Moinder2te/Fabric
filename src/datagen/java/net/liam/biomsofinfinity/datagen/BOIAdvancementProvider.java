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

public final class BOIAdvancementProvider implements DataProvider {
    private final FabricDataOutput output;

    public BOIAdvancementProvider(FabricDataOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");

        futures.add(save(cachedOutput, provider, "root", rootAdvancement()));
        futures.add(save(cachedOutput, provider, "luminous_crystal", luminousAdvancement()));
        futures.add(save(cachedOutput, provider, "shadow_altar", altarAdvancement()));
        futures.add(save(cachedOutput, provider, "shadow_king", shadowKingAdvancement()));

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Bioms of Infinity advancements";
    }

    private static JsonObject rootAdvancement() {
        JsonObject json = baseDisplay("glowshroom_spore", "task", "advancements.biomsofinfinity.root.title", "advancements.biomsofinfinity.root.description");
        json.addProperty("background", "minecraft:textures/gui/advancements/backgrounds/end.png");
        json.addProperty("show_toast", true);
        json.addProperty("announce_to_chat", true);
        json.addProperty("hidden", false);
        JsonObject root = new JsonObject();
        root.add("display", json);
        root.add("criteria", inventoryCriterion("obtain_spore", "glowshroom_spore"));
        return root;
    }

    private static JsonObject luminousAdvancement() {
        JsonObject display = baseDisplay("luminous_crystal", "goal", "advancements.biomsofinfinity.luminous.title", "advancements.biomsofinfinity.luminous.description");
        JsonObject json = new JsonObject();
        json.addProperty("parent", new ResourceLocation(MOD_ID, "root").toString());
        json.add("display", display);
        json.add("criteria", inventoryCriterion("craft_luminous", "luminous_crystal"));
        return json;
    }

    private static JsonObject altarAdvancement() {
        JsonObject display = baseDisplay("shadow_heart", "challenge", "advancements.biomsofinfinity.altar.title", "advancements.biomsofinfinity.altar.description");
        JsonObject json = new JsonObject();
        json.addProperty("parent", new ResourceLocation(MOD_ID, "luminous_crystal").toString());
        json.add("display", display);
        json.add("criteria", inventoryCriterion("obtain_heart", "shadow_heart"));
        return json;
    }

    private static JsonObject shadowKingAdvancement() {
        JsonObject display = baseDisplay("shadowsteel_chestplate", "challenge", "advancements.biomsofinfinity.shadow_king.title", "advancements.biomsofinfinity.shadow_king.description");
        JsonObject json = new JsonObject();
        json.addProperty("parent", new ResourceLocation(MOD_ID, "shadow_altar").toString());
        json.add("display", display);
        json.add("criteria", entityKilledCriterion("defeat_shadow_king", new ResourceLocation(MOD_ID, "shadow_king")));
        return json;
    }

    private static JsonObject baseDisplay(String iconItem, String frame, String titleKey, String descriptionKey) {
        JsonObject display = new JsonObject();
        JsonObject icon = new JsonObject();
        icon.addProperty("item", new ResourceLocation(MOD_ID, iconItem).toString());
        display.add("icon", icon);
        display.add("title", translate(titleKey));
        display.add("description", translate(descriptionKey));
        display.addProperty("frame", frame);
        display.addProperty("show_toast", true);
        display.addProperty("announce_to_chat", true);
        display.addProperty("hidden", false);
        return display;
    }

    private static JsonObject translate(String key) {
        JsonObject json = new JsonObject();
        json.addProperty("translate", key);
        return json;
    }

    private static JsonObject inventoryCriterion(String name, String itemId) {
        JsonObject criteria = new JsonObject();
        JsonObject entry = new JsonObject();
        entry.addProperty("trigger", "minecraft:inventory_changed");
        JsonObject conditions = new JsonObject();
        JsonArray items = new JsonArray();
        JsonObject item = new JsonObject();
        JsonArray itemList = new JsonArray();
        itemList.add(new ResourceLocation(MOD_ID, itemId).toString());
        item.add("items", itemList);
        items.add(item);
        conditions.add("items", items);
        entry.add("conditions", conditions);
        criteria.add(name, entry);
        return criteria;
    }

    private static JsonObject entityKilledCriterion(String name, ResourceLocation entityId) {
        JsonObject criteria = new JsonObject();
        JsonObject entry = new JsonObject();
        entry.addProperty("trigger", "minecraft:player_killed_entity");
        JsonObject conditions = new JsonObject();
        JsonObject entity = new JsonObject();
        entity.addProperty("type", entityId.toString());
        conditions.add("entity", entity);
        entry.add("conditions", conditions);
        criteria.add(name, entry);
        return criteria;
    }

    private static CompletableFuture<?> save(CachedOutput output, PackOutput.PathProvider provider, String name, JsonObject json) {
        Path path = provider.json(new ResourceLocation(MOD_ID, name));
        return DataProvider.saveStable(output, json, path);
    }
}
