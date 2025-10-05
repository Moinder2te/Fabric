# Bioms of Infinity

Bioms of Infinity expands the End dimension with bespoke biomes, structures, bosses, and crafting systems. This branch contains the first milestone focusing on the Glowshroom Forest, Crystal Cliffs, and Shadow Isles.

## Getting Started

* **Requirements:** Java 21 (Temurin) and the supplied Gradle wrapper. Fabric Loader 0.16.9 with Fabric API 0.118.0+1.21.8 are configured in `gradle.properties`.
* **Development tasks:**
  * `./gradlew runClient` – launches a development client.
  * `./gradlew build` – builds the mod jar.
  * `./gradlew datagen` – emits data packs and assets defined in the datagen module.

## Content Overview

### Biomes
* **Glowshroom Forest** – luminous fungi, glowshroom caps, and ambient spores.
* **Crystal Cliffs** – jagged amethyst formations and rich crystalite ore.
* **Shadow Isles** – home to the Shadow Altar, shadow blooms, and the Shadow King.

### Boss Progression
* Bosses can only be summoned through configured altars.
* The Shadow King requires a Shadow Heart used on the Shadow Altar within the Shadow Isles.
* Global and per-boss cooldowns are persisted via `BossCooldownManager`. Commands (`/boi boss enable|disable|resetcooldowns`) help testing.

### Crafting Systems
* **Infusion Table, Soul Forge, Lumina Workbench** provide staging for upcoming crafting recipes. They currently share container logic and drop their inventories when broken.

### Materials & Gear
* Crystalite, Shadowsteel, Aetherium, Aurorite, Soulglass materials with tooltips describing their intended functionality.
* Crystalite Sword applies glowing, Shadowsteel Chestplate grants short resistance pulses while worn.

## Configuration

`BOIConfig` automatically creates `config/biomsofinfinity/config.json5` with sections for worldgen, bosses, gameplay, and debug flags. Modify values and use `/boi config reload` in-game to apply changes.

Example snippet:
```json5
{
  "worldgen": {
    "enableAllBiomes": true,
    "biomeWeights": {
      "glowshroom_forest": 8,
      "shadow_isles": 2,
      "crystal_cliffs": 5
    }
  },
  "bosses": {
    "allowBossSpawns": true,
    "altarSpawnsOnly": true,
    "cooldowns": { "globalDays": 7, "perBossDays": 20 }
  }
}
```

## Asset Pipeline

* Data-driven worldgen, loot tables, and advancements are versioned in `src/main/resources/data` and mirrored by Fabric datagen providers under `src/datagen`.
* Models reference vanilla placeholder textures; artists can replace them later by supplying textures under `assets/biomsofinfinity/textures`.
* Sounds reference vanilla events through `sounds.json` to avoid missing assets.

## Extending the Project

1. Add new blocks/items in `registry/ModBlocks` and `registry/ModItems`. Register associated block entities or entities under `registry/`.
2. Define biome behaviour in `content/biome/` and register new biomes in `ModBiomes`, respecting config-driven weights.
3. Provide JSON resources (blockstates, models, loot, recipes, worldgen) and mirror them in the datagen module for reproducibility.
4. Gate any new bosses behind explicit summoning logic using `BaseBossEntity` patterns and the cooldown manager.

## Debug Commands

* `/boi config reload` – reloads the JSON config file.
* `/boi boss enable|disable` – toggles altar activations globally.
* `/boi boss resetcooldowns` – clears the cooldown state for testing.

Enjoy building upon Bioms of Infinity!
