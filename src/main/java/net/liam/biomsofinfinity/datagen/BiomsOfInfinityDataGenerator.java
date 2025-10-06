package net.liam.biomsofinfinity.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Placeholder data generator entrypoint. We only register pack output to keep builds successful
 * until dedicated providers are implemented.
 */
public final class BiomsOfInfinityDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        // No-op for now; providers will be added as content is implemented.
    }
}
