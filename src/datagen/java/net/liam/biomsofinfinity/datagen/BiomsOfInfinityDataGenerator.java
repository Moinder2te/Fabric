package net.liam.biomsofinfinity.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class BiomsOfInfinityDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(BOIAssetProvider::new);
        pack.addProvider(BOILootTableProvider::new);
        pack.addProvider(BOIRecipeProvider::new);
        pack.addProvider(BOIAdvancementProvider::new);
        pack.addProvider(BOIWorldgenProvider::new);
    }
}
