package mcp.mobius.waila.plugin.vanilla.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;

public class ArmorComponent extends GuiComponent implements ITooltipComponent {

    private final int armor;
    private final int iconCount;
    private final int lineWidth;

    public ArmorComponent(int armor, int maxPerLine) {
        this.armor = armor;
        this.iconCount = Mth.positiveCeilDiv(armor, 2);
        this.lineWidth = Math.min(iconCount, maxPerLine);
    }

    @Override
    public int getWidth() {
        return (lineWidth * 8) + 1;
    }

    @Override
    public int getHeight() {
        return (Mth.positiveCeilDiv(iconCount, lineWidth) * 3) + 6;
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        RenderSystem.enableBlend();

        int filled = armor / 2 - 1;
        int half = filled + armor % 2;

        for (int i = iconCount - 1; i >= 0; i--) {
            int ix = x + ((i % lineWidth) * 8);
            int iy = y + ((i / lineWidth) * 3);

            if (i <= filled) {
                blit(matrices, ix, iy, 34, 9, 9, 9);
            } else if (i == half) {
                blit(matrices, ix, iy, 25, 9, 9, 9);
            }
        }

        RenderSystem.disableBlend();
    }

}