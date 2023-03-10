package mcp.mobius.waila.plugin.core.theme;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import mcp.mobius.waila.api.ITheme;
import mcp.mobius.waila.api.IThemeAccessor;
import mcp.mobius.waila.api.IThemeType;
import mcp.mobius.waila.api.IntFormat;
import mcp.mobius.waila.api.__internal__.IApiService;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class GradientTheme implements ITheme {

    public static final IThemeType<GradientTheme> TYPE = IThemeType.of(GradientTheme.class)
        .withOptionalBool("square", false, false)
        .withInt("backgroundColor", IntFormat.RGB_HEX, 0xFF0000)
        .withInt("gradientStart", IntFormat.RGB_HEX, 0x00FF00)
        .withInt("gradientEnd", IntFormat.RGB_HEX, 0x0000FF)
        .withInt("fontColor", IntFormat.RGB_HEX, 0xA0A0A0)
        .build();

    private boolean square;
    private int backgroundColor;
    private int gradientStart;
    private int gradientEnd;
    private int fontColor;

    @Override
    public void init(IThemeAccessor accessor) {
        backgroundColor = Mth.clamp(backgroundColor, 0x000000, 0xFFFFFF);
        gradientStart = Mth.clamp(gradientStart, 0x000000, 0xFFFFFF);
        gradientEnd = Mth.clamp(gradientEnd, 0x000000, 0xFFFFFF);
        fontColor = Mth.clamp(fontColor, 0x000000, 0xFFFFFF);
    }

    @Override
    public int getFontColor() {
        return fontColor;
    }

    @Override
    public int[] getPadding() {
        return new int[]{4};
    }

    @Override
    public void renderTooltipBackground(PoseStack matrices, int x, int y, int width, int height, float alpha) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buf = tesselator.getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix = matrices.last().pose();

        int a = ((int) (0xFF * alpha)) << 24;
        int bg = backgroundColor + a;
        int gradStart = gradientStart + a;
        int gradEnd = gradientEnd + a;

        if (square) {
            IApiService.INSTANCE.fillGradient(matrix, buf, x, y, width, height, bg, bg);
        } else {
            // @formatter:off
            IApiService.INSTANCE.fillGradient(matrix, buf, x + 1        , y    , width - 2, height    , bg, bg);
            IApiService.INSTANCE.fillGradient(matrix, buf, x            , y + 1, 1        , height - 2, bg, bg);
            IApiService.INSTANCE.fillGradient(matrix, buf, x + width - 1, y + 1, 1        , height - 2, bg, bg);
            // @formatter:on
        }

        IApiService.INSTANCE.renderRectBorder(matrix, buf, x + 1, y + 1, width - 2, height - 2, gradStart, gradEnd);

        tesselator.end();
        RenderSystem.enableTexture();
    }

}
