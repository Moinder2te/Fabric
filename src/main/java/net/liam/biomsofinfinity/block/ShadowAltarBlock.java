package net.liam.biomsofinfinity.block;

import net.liam.biomsofinfinity.BiomsOfInfinityMod;
import net.liam.biomsofinfinity.block.entity.ShadowAltarBlockEntity;
import net.liam.biomsofinfinity.config.BOIConfig;
import net.liam.biomsofinfinity.content.boss.ShadowKingEntity;
import net.liam.biomsofinfinity.persistent.BossCooldownManager;
import net.liam.biomsofinfinity.registry.ModBiomes;
import net.liam.biomsofinfinity.registry.ModEntities;
import net.liam.biomsofinfinity.registry.ModItems;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShadowAltarBlock extends BlockWithEntity {
    public static final BooleanProperty ACTIVE = Properties.LIT;

    public ShadowAltarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShadowAltarBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (world.isClient) {
            return stack.isOf(ModItems.SHADOW_HEART) ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        ServerWorld serverWorld = (ServerWorld) world;
        BOIConfig config = BiomsOfInfinityMod.getConfig();
        if (!config.bosses.allowBossSpawns) {
            player.sendMessage(Text.translatable("message.biomsofinfinity.boss.disabled"), true);
            return ActionResult.CONSUME;
        }
        if (!stack.isOf(ModItems.SHADOW_HEART)) {
            player.sendMessage(Text.translatable("message.biomsofinfinity.shadow_altar.need_heart"), true);
            return ActionResult.CONSUME;
        }
        if (!serverWorld.getBiome(pos).matchesKey(ModBiomes.SHADOW_ISLES_KEY)) {
            player.sendMessage(Text.translatable("message.biomsofinfinity.shadow_altar.wrong_biome"), true);
            return ActionResult.CONSUME;
        }

        BossCooldownManager cooldowns = BossCooldownManager.get(serverWorld.getServer());
        long time = serverWorld.getTime();
        if (!cooldowns.canSummon(ShadowKingEntity.BOSS_ID, time, config)) {
            long remaining = Math.max(cooldowns.getRemainingForBoss(ShadowKingEntity.BOSS_ID, time), cooldowns.getRemainingGlobal(time));
            player.sendMessage(Text.translatable("message.biomsofinfinity.shadow_altar.on_cooldown", remaining / 20), true);
            return ActionResult.CONSUME;
        }

        ShadowKingEntity boss = ModEntities.SHADOW_KING.create(serverWorld);
        if (boss == null) {
            return ActionResult.FAIL;
        }

        boss.refreshPositionAndAngles(pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, world.random.nextFloat() * 360.0F, 0.0F);
        boss.onSummoned(serverWorld.getServer(), config);
        serverWorld.spawnEntity(boss);
        serverWorld.playSound(null, pos, SoundEvents.ENTITY_WITHER_SPAWN, boss.getSoundCategory(), 1.0F, 1.0F);
        stack.decrement(1);
        player.sendMessage(Text.translatable("message.biomsofinfinity.shadow_altar.spawn"), true);
        world.setBlockState(pos, state.with(ACTIVE, true));
        world.scheduleBlockTick(pos, this, 200);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ShadowAltarBlockEntity altar) {
            altar.setLastActivationTick(time);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        world.setBlockState(pos, state.with(ACTIVE, false));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
