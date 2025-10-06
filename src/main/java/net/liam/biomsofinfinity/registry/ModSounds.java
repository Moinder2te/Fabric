package net.liam.biomsofinfinity.registry;

import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ModSounds {
    private static final Map<Identifier, SoundEvent> REGISTERED_SOUNDS = new LinkedHashMap<>();

    public static final SoundEvent SHADOW_KING_SUMMON = register("entity.shadow_king.summon");
    public static final SoundEvent SHADOW_KING_PHASE_SHIFT = register("entity.shadow_king.phase_shift");
    public static final SoundEvent INFUSION_TABLE_AMBIENT = register("block.infusion_table.ambient");

    private ModSounds() {}

    private static SoundEvent register(String name) {
        Identifier id = BiomsOfInfinityMod.id(name);
        SoundEvent event = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, event);
        REGISTERED_SOUNDS.put(id, event);
        return event;
    }

    public static void init() {
        // static initialisation
    }
}
