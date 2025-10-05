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

public class LuminaWorkbenchBlockEntity extends AbstractWorkshopBlockEntity {
    public LuminaWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LUMINA_WORKBENCH, pos, state, 9, Text.translatable("container.biomsofinfinity.lumina_workbench"));
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this, 1);
    }
}
