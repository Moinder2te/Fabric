package net.liam.biomsofinfinity.item;

import net.liam.biomsofinfinity.item.material.BOIArmorMaterials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowsteelChestplateItem extends ArmorItem {
    public ShadowsteelChestplateItem(Settings settings) {
        super(BOIArmorMaterials.SHADOWSTEEL, Type.CHESTPLATE, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof LivingEntity living) {
            ItemStack equipped = living.getEquippedStack(EquipmentSlot.CHEST);
            if (equipped == stack) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, true, false));
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.biomsofinfinity.shadowsteel_chestplate"));
    }
}
