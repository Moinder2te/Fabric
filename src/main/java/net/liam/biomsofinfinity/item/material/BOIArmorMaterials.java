package net.liam.biomsofinfinity.item.material;

import net.liam.biomsofinfinity.registry.ModItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public enum BOIArmorMaterials implements ArmorMaterial {
    SHADOWSTEEL("shadowsteel", 37, createProtectionMap(4, 7, 9, 4), 20, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.ofItems(ModItems.SHADOWSTEEL_INGOT));

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

    private final String name;
    private final int durabilityMultiplier;
    private final Map<ArmorItem.Type, Integer> protection;
    private final int enchantability;
    private final net.minecraft.sound.SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    BOIArmorMaterials(String name, int durabilityMultiplier, Map<ArmorItem.Type, Integer> protection, int enchantability, net.minecraft.sound.SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    private static Map<ArmorItem.Type, Integer> createProtectionMap(int boots, int leggings, int chest, int helmet) {
        Map<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.CHESTPLATE, chest);
        map.put(ArmorItem.Type.HELMET, helmet);
        return map;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protection.getOrDefault(type, 0);
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public net.minecraft.sound.SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
