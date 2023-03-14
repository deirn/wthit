package mcp.mobius.waila.api.__internal__;

import java.util.Collection;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.api.IBlacklistConfig;
import mcp.mobius.waila.api.IJsonConfig;
import mcp.mobius.waila.api.IModInfo;
import mcp.mobius.waila.api.IPickerAccessor;
import mcp.mobius.waila.api.IPickerResults;
import mcp.mobius.waila.api.IPluginInfo;
import mcp.mobius.waila.api.ITheme;
import mcp.mobius.waila.api.IThemeType;
import mcp.mobius.waila.api.ITooltipComponent;
import mcp.mobius.waila.api.IWailaConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix4f;

@ApiStatus.Internal
public interface IApiService {

    IApiService INSTANCE = Internals.loadService(IApiService.class);

    IBlacklistConfig getBlacklistConfig();

    <T> IJsonConfig.Builder0<T> createConfigBuilder(Class<T> clazz);

    IModInfo getModInfo(String namespace);

    IModInfo getModInfo(ItemStack stack);

    IPluginInfo getPluginInfo(ResourceLocation pluginId);

    Collection<IPluginInfo> getAllPluginInfoFromMod(String modId);

    Collection<IPluginInfo> getAllPluginInfo();

    IWailaConfig getConfig();

    void renderItem(int x, int y, ItemStack stack);

    void renderComponent(PoseStack matrices, ITooltipComponent component, int x, int y, float delta);

    int getPairComponentColonOffset();

    int getColonFontWidth();

    int getFontColor();

    void fillGradient(Matrix4f matrix, BufferBuilder buf, int x, int y, int w, int h, int start, int end);

    void renderRectBorder(Matrix4f matrix, BufferBuilder buf, int x, int y, int w, int h, int gradStart, int gradEnd);

    <T extends ITheme> IThemeType.Builder<T> createThemeDescBuilder(Class<T> clazz);

    @Deprecated
    IPickerAccessor getPickerAccessor();

    @Deprecated
    IPickerResults getPickerResults();

}
