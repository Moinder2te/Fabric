package net.liam.biomsofinfinity.content.boss;

import net.liam.biomsofinfinity.config.BOIConfig;
import net.liam.biomsofinfinity.persistent.BossCooldownManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public abstract class BaseBossEntity extends HostileEntity {
    protected final ServerBossBar bossBar;
    protected BossPhase phase = BossPhase.PHASE_ONE;
    private int phaseTicks = 0;

    protected BaseBossEntity(EntityType<? extends HostileEntity> entityType, World world, String translationKey, BossBar.Color color) {
        super(entityType, world);
        this.experiencePoints = 60;
        this.bossBar = new ServerBossBar(Text.translatable(translationKey), color, BossBar.Style.PROGRESS);
        this.bossBar.setPercent(1.0F);
        this.bossBar.setDarkenSky(true);
    }

    public static DefaultAttributeContainer.Builder createBaseAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 400.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (!this.world.isClient) {
            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
            this.phaseTicks++;
            BossPhase next = BossPhase.forHealthFraction(this.getHealth() / this.getMaxHealth());
            if (next != this.phase) {
                this.phase = next;
                onPhaseChanged(next);
            }
            handlePhaseActions();
        }
    }

    protected void playTelegraph(SoundEvent sound) {
        if (sound != null) {
            this.world.playSoundFromEntity(null, this, sound, this.getSoundCategory(), 1.0F, 1.0F);
        }
    }

    protected int getPhaseTicks() {
        return phaseTicks;
    }

    protected abstract void handlePhaseActions();

    protected abstract SoundEvent getPhaseTransitionSound(BossPhase phase);

    protected abstract String getBossId();

    protected void onPhaseChanged(BossPhase newPhase) {
        phaseTicks = 0;
        SoundEvent sound = getPhaseTransitionSound(newPhase);
        if (sound != null) {
            playTelegraph(sound);
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    protected void onKilledBy(PlayerEntity player) {
        super.onKilledBy(player);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (!world.isClient && world.getServer() != null && reason != RemovalReason.CHANGED_DIMENSION) {
            BossCooldownManager.get(world.getServer()).recordDefeat(getBossId());
        }
    }

    public void onSummoned(MinecraftServer server, BOIConfig config) {
        BossCooldownManager.get(server).recordSummon(getBossId(), server.getOverworld().getTime(), config);
    }

    public enum BossPhase {
        PHASE_ONE,
        PHASE_TWO,
        PHASE_THREE;

        public static BossPhase forHealthFraction(float fraction) {
            if (fraction <= 0.33F) {
                return PHASE_THREE;
            }
            if (fraction <= 0.66F) {
                return PHASE_TWO;
            }
            return PHASE_ONE;
        }
    }
}
