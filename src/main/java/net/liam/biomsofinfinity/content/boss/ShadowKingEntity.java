package net.liam.biomsofinfinity.content.boss;

import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class ShadowKingEntity extends BaseBossEntity {
    public static final String BOSS_ID = "shadow_king";

    public ShadowKingEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, "boss." + BiomsOfInfinityMod.MOD_ID + ".shadow_king", BossBar.Color.PURPLE);
        this.setPersistenceRequired();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return BaseBossEntity.createBaseAttributes()
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 16.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 450.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.6D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 32.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new net.minecraft.entity.ai.goal.ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void handlePhaseActions() {
        if (this.phase == BossPhase.PHASE_TWO && getPhaseTicks() % 160 == 0) {
            castShadowPulse(6.0D, 6);
        }
        if (this.phase == BossPhase.PHASE_THREE && getPhaseTicks() % 120 == 0) {
            castShadowPulse(10.0D, 10);
            summonShadowBolts();
        }
    }

    private void castShadowPulse(double radius, int durationSeconds) {
        this.playTelegraph(SoundEvents.ENTITY_WITHER_SHOOT);
        this.getWorld().getPlayers().stream()
            .filter(player -> player.squaredDistanceTo(this) <= radius * radius)
            .forEach(player -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, durationSeconds * 20, 0, true, true)));
    }

    private void summonShadowBolts() {
        this.playTelegraph(SoundEvents.ENTITY_ENDER_DRAGON_GROWL);
        this.getWorld().getPlayers().stream()
            .filter(player -> player.squaredDistanceTo(this) <= 196.0D)
            .forEach(player -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 1, true, true)));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_BASALT_DELTAS_MOOD;
    }

    @Override
    protected SoundEvent getPhaseTransitionSound(BossPhase phase) {
        return switch (phase) {
            case PHASE_ONE -> SoundEvents.BLOCK_END_PORTAL_FRAME_FILL;
            case PHASE_TWO -> SoundEvents.ENTITY_WITHER_SPAWN;
            case PHASE_THREE -> SoundEvents.ENTITY_ENDER_DRAGON_GROWL;
        };
    }

    @Override
    protected String getBossId() {
        return BOSS_ID;
    }
}
