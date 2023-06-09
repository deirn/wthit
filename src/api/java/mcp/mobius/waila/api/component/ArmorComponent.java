package mcp.mobius.waila.api.component;

import mcp.mobius.waila.api.ITooltipComponent;
import mcp.mobius.waila.api.WailaHelper;
import mcp.mobius.waila.api.__internal__.ApiSide;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

/**
 * Component that renders an armor bar.
 */
@ApiSide.ClientOnly
public class ArmorComponent implements ITooltipComponent {

    /**
     * @param armor      the armor points, 1 full icon represent 2 points
     * @param maxPerLine the max icon per line until it get wrapped into multiple
     */
    public ArmorComponent(int armor, int maxPerLine) {
        this.armor = armor;
        this.iconCount = Mth.positiveCeilDiv(armor, 2);
        this.lineWidth = Math.min(iconCount, maxPerLine);
    }

    private final int armor;
    private final int iconCount;
    private final int lineWidth;

    @Override
    public int getWidth() {
        return (lineWidth * 8) + 1;
    }

    @Override
    public int getHeight() {
        return (Mth.positiveCeilDiv(iconCount, lineWidth) * 3) + 6;
    }

    @Override
    public void render(GuiGraphics ctx, int x, int y, float delta) {
        int filled = armor / 2 - 1;
        int half = filled + armor % 2;

        for (int i = iconCount - 1; i >= 0; i--) {
            int ix = x + ((i % lineWidth) * 8);
            int iy = y + ((i / lineWidth) * 3);

            if (i <= filled) {
                ctx.blit(WailaHelper.GUI_ICONS_TEXTURE, ix, iy, 34, 9, 9, 9);
            } else if (i == half) {
                ctx.blit(WailaHelper.GUI_ICONS_TEXTURE, ix, iy, 25, 9, 9, 9);
            }
        }
    }

}
