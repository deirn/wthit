package mcp.mobius.waila.gui.hud;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.api.ITooltipComponent;
import mcp.mobius.waila.api.ITooltipLine;
import mcp.mobius.waila.api.component.GrowingComponent;
import mcp.mobius.waila.api.component.WrappedComponent;
import mcp.mobius.waila.util.DisplayUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class Line implements ITooltipLine {

    @Nullable
    public final ResourceLocation tag;
    public final List<ITooltipComponent> components = new ArrayList<>();

    private int width = -1;
    private int height = -1;
    private int growingCount = 0;

    public Line(@Nullable ResourceLocation tag) {
        this.tag = tag;
    }

    @Override
    public Line with(ITooltipComponent component) {
        components.add(component);
        height = Math.max(component.getHeight(), height);
        if (component instanceof GrowingComponent) {
            growingCount++;
        }
        return this;
    }

    @Override
    public Line with(Component component) {
        return with(new WrappedComponent(component));
    }

    public void calculateDimension() {
        if (width == -1) {
            width = components.stream().mapToInt(c -> {
                int width = c.getWidth();
                return width > 0 ? width + 1 : 0;
            }).sum();
            if (width > 0) {
                width--;
            }
        }

        if (height == -1) {
            height = components.stream().mapToInt(ITooltipComponent::getHeight).max().orElse(0);
        }
    }

    public int getWidth() {
        assertDimension();
        return width;
    }

    public int getHeight() {
        assertDimension();
        return height;
    }

    public void render(PoseStack matrices, int x, int y, int maxWidth, float delta) {
        assertDimension();

        int cx = x;
        int growingWidth = -1;
        for (ITooltipComponent component : components) {
            if (component instanceof GrowingComponent) {
                if (growingWidth == -1) {
                    growingWidth = (maxWidth - width) / growingCount;
                    if (growingWidth % 2 == 1 && growingCount > 1) {
                        cx++;
                    }
                }
                cx += growingWidth;
                continue;
            }

            int w = component.getWidth();
            int h = component.getHeight();
            if (w <= 0) {
                continue;
            }

            int cy = y + (h < height ? (height - h) / 2 : 0);
            DisplayUtil.renderComponent(matrices, component, cx, cy, delta);
            cx += w + 1;
        }
    }

    private void assertDimension() {
        Preconditions.checkState(width != -1 && height != -1);
    }

}
