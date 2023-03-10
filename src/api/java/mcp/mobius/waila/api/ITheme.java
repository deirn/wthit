package mcp.mobius.waila.api;

import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.api.__internal__.ApiSide;
import org.jetbrains.annotations.ApiStatus;

@ApiSide.ClientOnly
@ApiStatus.OverrideOnly
@ApiStatus.Experimental
public interface ITheme {

    default void init(IThemeAccessor accessor) {
    }

    int getFontColor();

    int[] getPadding();

    void renderTooltipBackground(PoseStack matrices, int x, int y, int width, int height, float alpha);

}
