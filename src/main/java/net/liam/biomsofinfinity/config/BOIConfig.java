package net.liam.biomsofinfinity.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;
import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BOIConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("biomsofinfinity");
    private static final Path CONFIG_PATH = CONFIG_DIR.resolve("config.json5");

    public Worldgen worldgen = new Worldgen();
    public Bosses bosses = new Bosses();
    public Gameplay gameplay = new Gameplay();
    public Debug debug = new Debug();

    public static BOIConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (BufferedReader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
                BOIConfig config = GSON.fromJson(reader, BOIConfig.class);
                if (config == null) {
                    config = defaults();
                }
                config.fillMissing();
                return config;
            } catch (IOException | JsonParseException exception) {
                BiomsOfInfinityMod.LOGGER.warn("Failed to read BOI config, using defaults", exception);
            }
        }

        BOIConfig defaults = defaults();
        save(defaults);
        return defaults;
    }

    public static void save(BOIConfig config) {
        try {
            if (Files.notExists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
            try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException exception) {
            BiomsOfInfinityMod.LOGGER.error("Failed to save BOI config", exception);
        }
    }

    private void fillMissing() {
        if (worldgen == null) {
            worldgen = new Worldgen();
        }
        worldgen.fillMissing();
        if (bosses == null) {
            bosses = new Bosses();
        }
        bosses.fillMissing();
        if (gameplay == null) {
            gameplay = new Gameplay();
        }
        if (debug == null) {
            debug = new Debug();
        }
    }

    public static BOIConfig defaults() {
        BOIConfig config = new BOIConfig();
        config.fillMissing();
        return config;
    }

    public static class Worldgen {
        public boolean enableAllBiomes = true;
        public Map<String, Integer> biomeWeights = defaultMap(() -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("glowshroom_forest", 8);
            map.put("shadow_isles", 2);
            map.put("crystal_cliffs", 5);
            return map;
        });
        public Map<String, Integer> structureWeights = defaultMap(() -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("crystal_tower", 3);
            map.put("lost_temple", 1);
            return map;
        });

        public int getBiomeWeight(String key, int fallback) {
            return biomeWeights.getOrDefault(key, fallback);
        }

        public int getStructureWeight(String key, int fallback) {
            return structureWeights.getOrDefault(key, fallback);
        }

        private void fillMissing() {
            if (biomeWeights == null) {
                biomeWeights = new HashMap<>();
            }
            if (structureWeights == null) {
                structureWeights = new HashMap<>();
            }
        }
    }

    public static class Bosses {
        public boolean allowBossSpawns = true;
        public boolean naturalBossSpawns = false;
        public boolean altarSpawnsOnly = true;
        public Map<String, Integer> cooldowns = defaultMap(() -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("globalDays", 7);
            map.put("perBossDays", 20);
            return map;
        });
        public int maxConcurrentBosses = 1;
        public boolean announceBossEvents = true;

        private void fillMissing() {
            if (cooldowns == null) {
                cooldowns = new HashMap<>();
            }
            cooldowns.putIfAbsent("globalDays", 7);
            cooldowns.putIfAbsent("perBossDays", 20);
        }

        public long toWorldTicks(String key, long defaultDays) {
            int days = cooldowns.getOrDefault(key, (int) defaultDays);
            return days * 24000L;
        }
    }

    public static class Gameplay {
        public boolean aetherSlowFallingInBiome = true;
        public int stormProjectileShieldTicks = 60;
    }

    public static class Debug {
        public boolean logAllRegistrations = false;
        public boolean datagenForce = false;
    }

    private static <T> Map<String, T> defaultMap(Supplier<Map<String, T>> supplier) {
        return supplier.get();
    }

    public void mergeAndSave(BOIConfig newConfig) {
        if (newConfig == null) {
            return;
        }
        this.worldgen = newConfig.worldgen;
        this.bosses = newConfig.bosses;
        this.gameplay = newConfig.gameplay;
        this.debug = newConfig.debug;
        save(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "BOIConfig{" +
            "worldgen=" + worldgen +
            ", bosses=" + bosses +
            ", gameplay=" + gameplay +
            ", debug=" + debug +
            '}';
    }
}
