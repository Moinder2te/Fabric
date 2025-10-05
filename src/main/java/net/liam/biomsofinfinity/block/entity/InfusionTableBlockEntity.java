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

public class InfusionTableBlockEntity extends AbstractWorkshopBlockEntity {
    public InfusionTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INFUSION_TABLE, pos, state, 9, Text.translatable("container.biomsofinfinity.infusion_table"));
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this, 1);
    }
}
