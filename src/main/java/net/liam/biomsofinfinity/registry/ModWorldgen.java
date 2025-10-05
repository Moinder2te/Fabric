package net.liam.biomsofinfinity.registry;

import net.liam.biomsofinfinity.BiomsOfInfinityMod;

public final class ModWorldgen {
    private ModWorldgen() {}

    public static void init() {
        // All world generation is data-driven for 1.21+. This hook exists for future code-based wiring.
        BiomsOfInfinityMod.LOGGER.debug("Worldgen registry bootstrapped");
    }
}
