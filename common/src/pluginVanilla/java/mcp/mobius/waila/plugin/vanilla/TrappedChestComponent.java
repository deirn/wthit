package mcp.mobius.waila.plugin.vanilla;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.ChestBlock.FACING;
import static net.minecraft.world.level.block.ChestBlock.TYPE;
import static net.minecraft.world.level.block.ChestBlock.WATERLOGGED;

public enum TrappedChestComponent implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(WailaVanilla.CONFIG_HIDE_TRAPPED_CHEST)) {
            BlockState state = accessor.getBlockState();
            return Blocks.CHEST.defaultBlockState()
                .setValue(FACING, state.getValue(FACING))
                .setValue(TYPE, state.getValue(TYPE))
                .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
        }
        return null;
    }

}