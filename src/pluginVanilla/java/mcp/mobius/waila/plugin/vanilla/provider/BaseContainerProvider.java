package mcp.mobius.waila.plugin.vanilla.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ItemData;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;

public enum BaseContainerProvider implements IDataProvider<BaseContainerBlockEntity> {

    INSTANCE;

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BaseContainerBlockEntity> accessor, IPluginConfig config) {
        if (!accessor.getTarget().canOpen(accessor.getPlayer())) {
            data.add(ItemData.class, IDataWriter.Result::block);
        }
    }

}
