package net.liam.biomsofinfinity.block.entity;

import net.liam.biomsofinfinity.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SoulForgeBlockEntity extends AbstractWorkshopBlockEntity {
    public SoulForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FORGE, pos, state, 18, Text.translatable("container.biomsofinfinity.soul_forge"));
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, this, 2);
    }
}
