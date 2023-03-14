package mcp.mobius.waila.config;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.ITheme;
import mcp.mobius.waila.api.IWailaConfig;
import mcp.mobius.waila.gui.hud.theme.ThemeDefinition;
import mcp.mobius.waila.util.DisplayUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class WailaConfig implements IWailaConfig {

    private final General general = new General();
    private final Overlay overlay = new Overlay();
    private final Formatter formatter = new Formatter();
    private int configVersion = 0;

    public int getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(int configVersion) {
        this.configVersion = configVersion;
    }

    @Override
    public General getGeneral() {
        return general;
    }

    @Override
    public Overlay getOverlay() {
        return overlay;
    }

    @Override
    public Formatter getFormatter() {
        return formatter;
    }

    public static class General implements IWailaConfig.General {

        private boolean displayTooltip = true;
        private boolean shiftForDetails = false;
        private boolean hideShiftText = false;
        private DisplayMode displayMode = DisplayMode.TOGGLE;
        private boolean hideFromPlayerList = true;
        private boolean hideFromDebug = true;
        private boolean enableTextToSpeech = false;
        private int rateLimit = 250;

        @Override
        public boolean isDisplayTooltip() {
            return displayTooltip;
        }

        public void setDisplayTooltip(boolean displayTooltip) {
            this.displayTooltip = displayTooltip;
        }

        @Override
        public boolean isShiftForDetails() {
            return shiftForDetails;
        }

        public void setShiftForDetails(boolean shiftForDetails) {
            this.shiftForDetails = shiftForDetails;
        }

        @Override
        public boolean isHideShiftText() {
            return hideShiftText;
        }

        public void setHideShiftText(boolean hideShiftText) {
            this.hideShiftText = hideShiftText;
        }

        @Override
        public DisplayMode getDisplayMode() {
            return displayMode;
        }

        public void setDisplayMode(DisplayMode displayMode) {
            this.displayMode = displayMode;
        }

        @Override
        public boolean isHideFromPlayerList() {
            return hideFromPlayerList;
        }

        public void setHideFromPlayerList(boolean hideFromPlayerList) {
            this.hideFromPlayerList = hideFromPlayerList;
        }

        @Override
        public boolean isHideFromDebug() {
            return hideFromDebug;
        }

        public void setHideFromDebug(boolean hideFromDebug) {
            this.hideFromDebug = hideFromDebug;
        }

        @Override
        public boolean isEnableTextToSpeech() {
            return enableTextToSpeech;
        }

        public void setEnableTextToSpeech(boolean enableTextToSpeech) {
            this.enableTextToSpeech = enableTextToSpeech;
        }

        @Override
        public int getRateLimit() {
            rateLimit = Math.max(rateLimit, 250);
            return rateLimit;
        }

        public void setRateLimit(int rateLimit) {
            this.rateLimit = rateLimit;
        }

        @Override
        @SuppressWarnings("removal")
        public int getMaxHealthForRender() {
            return 40;
        }

        @Override
        @SuppressWarnings("removal")
        public int getMaxHeartsPerLine() {
            return 10;
        }

    }

    public static class Overlay implements IWailaConfig.Overlay {

        private final Position position = new Position();
        private final Color color = new Color();
        private float scale = 1.0F;

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public float getScale() {
            return scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        @Override
        public Color getColor() {
            return color;
        }

        public static class Position implements IWailaConfig.Overlay.Position {

            private final Align align = new Align();
            private final Align anchor = new Align();
            private int x = 0;
            private int y = 0;
            private boolean bossBarsOverlap = false;

            @Override
            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            @Override
            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            @Override
            public Align getAlign() {
                return align;
            }

            @Override
            public Align getAnchor() {
                return anchor;
            }

            @Override
            public boolean isBossBarsOverlap() {
                return bossBarsOverlap;
            }

            public void setBossBarsOverlap(boolean bossBarsOverlap) {
                this.bossBarsOverlap = bossBarsOverlap;
            }

            public static class Align implements IWailaConfig.Overlay.Position.Align {

                X x = X.CENTER;
                Y y = Y.TOP;

                @Override
                public X getX() {
                    return x;
                }

                public void setX(X x) {
                    this.x = x;
                }

                @Override
                public Y getY() {
                    return y;
                }

                public void setY(Y y) {
                    this.y = y;
                }

            }

        }

        @SuppressWarnings("removal")
        public static class Color implements IWailaConfig.Overlay.Color {

            private static final ResourceLocation DEFAULT = Waila.id("vanilla");

            private int alpha = 80;
            private ResourceLocation activeTheme = DEFAULT;

            private final Map<ResourceLocation, ThemeDefinition<?>> themes = new HashMap<>();

            @Override
            public int getAlpha() {
                return DisplayUtil.getAlphaFromPercentage(alpha);
            }

            @Override
            public ITheme getTheme() {
                Map<ResourceLocation, ThemeDefinition<?>> allTheme = ThemeDefinition.getAll();

                if (!allTheme.containsKey(activeTheme)) {
                    activeTheme = DEFAULT;
                    Waila.CONFIG.save();
                }

                return allTheme.get(activeTheme).getInitializedInstance();
            }

            public void setAlpha(int alpha) {
                this.alpha = alpha;
            }

            public int rawAlpha() {
                return alpha;
            }

            public Map<ResourceLocation, ThemeDefinition<?>> getCustomThemes() {
                return themes;
            }

            public ResourceLocation getActiveTheme() {
                return activeTheme;
            }

            @Override
            public int getBackgroundColor() {
                return 0;
            }

            @Override
            public int getGradientStart() {
                return 0;
            }

            @Override
            public int getGradientEnd() {
                return 0;
            }

            @Override
            public int getFontColor() {
                return getTheme().getFontColor();
            }

            public void applyTheme(ResourceLocation id) {
                Map<ResourceLocation, ThemeDefinition<?>> allTheme = ThemeDefinition.getAll();
                activeTheme = allTheme.containsKey(id) ? id : activeTheme;
            }

            public static class Adapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

                @Override
                public Color deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    JsonObject json = element.getAsJsonObject();
                    Color color = new Color();
                    color.alpha = json.getAsJsonPrimitive("alpha").getAsInt();
                    color.activeTheme = new ResourceLocation(json.getAsJsonPrimitive("activeTheme").getAsString());
                    json.getAsJsonArray("themes").forEach(e -> {
                        ThemeDefinition<?> themeDef = context.deserialize(e, ThemeDefinition.class);
                        color.themes.put(themeDef.id, themeDef);
                    });
                    return color;
                }

                @Override
                public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
                    JsonObject json = new JsonObject();
                    json.addProperty("alpha", src.alpha);
                    json.add("themes", context.serialize(src.themes.values()));
                    json.addProperty("activeTheme", src.activeTheme.toString());
                    return json;
                }

            }

        }

    }

    public static class Formatter implements IWailaConfig.Formatter {

        private String modName = "\u00A79\u00A7o%s";
        private String blockName = "\u00a7f%s";
        private String fluidName = "\u00a7f%s";
        private String entityName = "\u00a7f%s";
        private String registryName = "\u00a78%s";

        public String getModName() {
            return modName;
        }

        public void setModName(String modName) {
            this.modName = modName;
        }

        public String getBlockName() {
            return blockName;
        }

        public void setBlockName(String blockName) {
            this.blockName = blockName;
        }

        public String getFluidName() {
            return fluidName;
        }

        public void setFluidName(String fluidName) {
            this.fluidName = fluidName;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getRegistryName() {
            return registryName;
        }

        public void setRegistryName(String registryName) {
            this.registryName = registryName;
        }

        @Override
        public Component modName(Object modName) {
            return Component.literal(this.modName.formatted(modName));
        }

        @Override
        public Component blockName(Object blockName) {
            return Component.literal(this.blockName.formatted(blockName));
        }

        @Override
        public Component fluidName(Object fluidName) {
            return Component.literal(this.fluidName.formatted(fluidName));
        }

        @Override
        public Component entityName(Object entityName) {
            return Component.literal(this.entityName.formatted(entityName));
        }

        @Override
        public Component registryName(Object registryName) {
            return Component.literal(this.registryName.formatted(registryName));
        }

    }

}
