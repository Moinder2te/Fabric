package net.liam.biomsofinfinity.block.entity;

import net.liam.biomsofinfinity.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class ShadowAltarBlockEntity extends BlockEntity {
    private long lastActivationTick;

    public ShadowAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOW_ALTAR, pos, state);
    }

    public long getLastActivationTick() {
        return lastActivationTick;
    }

    public void setLastActivationTick(long lastActivationTick) {
        this.lastActivationTick = lastActivationTick;
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.lastActivationTick = nbt.getLong("LastActivationTick");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("LastActivationTick", lastActivationTick);
    }
}
