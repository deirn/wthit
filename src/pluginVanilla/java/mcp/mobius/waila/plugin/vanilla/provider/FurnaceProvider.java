package mcp.mobius.waila.plugin.vanilla.provider;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.component.ItemComponent;
import mcp.mobius.waila.api.component.ProgressArrowComponent;
import mcp.mobius.waila.mixin.AbstractFurnaceBlockEntityAccess;
import mcp.mobius.waila.plugin.vanilla.config.Options;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public enum FurnaceProvider implements IBlockComponentProvider, IServerDataProvider<AbstractFurnaceBlockEntity> {

    INSTANCE;

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(Options.FURNACE_CONTENTS) && accessor.getServerData().contains("progress")) {
            CompoundTag tag = accessor.getServerData();
            ListTag furnaceItems = tag.getList("furnace", Tag.TAG_COMPOUND);

            float progress = tag.getInt("progress");
            float total = tag.getInt("total");

            tooltip.addLine()
                .with(new ItemComponent(ItemStack.of(furnaceItems.getCompound(0))))
                .with(new ItemComponent(ItemStack.of(furnaceItems.getCompound(1))))
                .with(new ProgressArrowComponent(progress / total))
                .with(new ItemComponent(ItemStack.of(furnaceItems.getCompound(2))));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, IServerAccessor<AbstractFurnaceBlockEntity> accessor, IPluginConfig config) {
        if (config.getBoolean(Options.FURNACE_CONTENTS)) {
            AbstractFurnaceBlockEntity furnace = accessor.getTarget();
            AbstractFurnaceBlockEntityAccess access = (AbstractFurnaceBlockEntityAccess) furnace;
            if (furnace.getBlockState().getValue(FurnaceBlock.LIT)) {
                ListTag items = new ListTag();
                items.add(furnace.getItem(0).save(new CompoundTag()));
                items.add(furnace.getItem(1).save(new CompoundTag()));
                items.add(furnace.getItem(2).save(new CompoundTag()));
                data.put("furnace", items);

                data.putInt("progress", access.wthit_cookingProgress());
                data.putInt("total", access.wthit_cookingTotalTime());
            }
        }
    }

}
