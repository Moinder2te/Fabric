package net.liam.biomsofinfinity.item;

import net.liam.biomsofinfinity.item.material.BOIToolMaterials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystaliteSwordItem extends SwordItem {
    public CrystaliteSwordItem(Settings settings) {
        super(BOIToolMaterials.CRYSTALITE, settings); // In 1.21, SwordItem constructor takes ToolMaterial and settings
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.postHit(stack, target, attacker);
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0));
        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.biomsofinfinity.crystalite_sword"));
    }
}
