package net.liam.biomsofinfinity.persistent;

import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.config.BOIConfig;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class BossCooldownManager extends PersistentState {
    private static final String STORAGE_KEY = BiomsOfInfinityMod.MOD_ID + "_boss_cooldowns";

    private long globalCooldownUntil = 0L;
    private final Map<String, Long> perBossCooldowns = new HashMap<>();
    private int activeBosses = 0;

    public static BossCooldownManager get(MinecraftServer server) {
        PersistentStateManager manager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        return manager.getOrCreate(BossCooldownManager::fromNbt, BossCooldownManager::new, STORAGE_KEY);
    }

    public static BossCooldownManager fromNbt(NbtCompound nbt) {
        BossCooldownManager manager = new BossCooldownManager();
        manager.globalCooldownUntil = nbt.getLong("GlobalCooldown");
        manager.activeBosses = nbt.getInt("ActiveBosses");
        NbtCompound cooldowns = nbt.getCompound("BossCooldowns");
        for (String key : cooldowns.getKeys()) {
            manager.perBossCooldowns.put(key, cooldowns.getLong(key));
        }
        return manager;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putLong("GlobalCooldown", globalCooldownUntil);
        nbt.putInt("ActiveBosses", activeBosses);
        NbtCompound cooldowns = new NbtCompound();
        perBossCooldowns.forEach(cooldowns::putLong);
        nbt.put("BossCooldowns", cooldowns);
        return nbt;
    }

    public boolean canSummon(String bossId, long worldTime, BOIConfig config) {
        if (!config.bosses.allowBossSpawns) {
            return false;
        }
        if (config.bosses.altarSpawnsOnly && config.bosses.naturalBossSpawns) {
            // Natural spawns are disabled - nothing else to check here.
        }
        if (activeBosses >= config.bosses.maxConcurrentBosses) {
            return false;
        }
        if (worldTime < globalCooldownUntil) {
            return false;
        }
        long perBoss = perBossCooldowns.getOrDefault(bossId, 0L);
        return worldTime >= perBoss;
    }

    public void recordSummon(String bossId, long worldTime, BOIConfig config) {
        globalCooldownUntil = worldTime + config.bosses.toWorldTicks("globalDays", 7);
        perBossCooldowns.put(bossId, worldTime + config.bosses.toWorldTicks("perBossDays", 20));
        activeBosses++;
        markDirty();
    }

    public void recordDefeat(String bossId) {
        activeBosses = Math.max(0, activeBosses - 1);
        perBossCooldowns.putIfAbsent(bossId, 0L);
        markDirty();
    }

    public void warmup(BOIConfig config) {
        if (config.bosses.maxConcurrentBosses <= 0) {
            config.bosses.maxConcurrentBosses = 1;
        }
    }

    public void reset() {
        globalCooldownUntil = 0L;
        perBossCooldowns.clear();
        activeBosses = 0;
        markDirty();
    }

    public long getRemainingGlobal(long worldTime) {
        return Math.max(0L, globalCooldownUntil - worldTime);
    }

    public long getRemainingForBoss(String bossId, long worldTime) {
        return Math.max(0L, perBossCooldowns.getOrDefault(bossId, 0L) - worldTime);
    }

    public int getActiveBosses() {
        return activeBosses;
    }
}
