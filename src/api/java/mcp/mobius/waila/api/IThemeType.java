package mcp.mobius.waila.api;

import mcp.mobius.waila.api.__internal__.ApiSide;
import mcp.mobius.waila.api.__internal__.IApiService;
import org.jetbrains.annotations.ApiStatus;

/**
 * A description of a theme constructor. Decides how a theme is serialized.
 *
 * @param <T> the theme type.
 */
@ApiSide.ClientOnly
@ApiStatus.NonExtendable
@ApiStatus.Experimental
@SuppressWarnings("unused")
public interface IThemeType<T extends ITheme> {

    /**
     * Creates a theme type builder.
     *
     * @param clazz the theme class
     */
    static <T extends ITheme> Builder<T> of(Class<T> clazz) {
        return IApiService.INSTANCE.createThemeDescBuilder(clazz);
    }

    interface Builder<T extends ITheme> {

        Builder<T> withInt(String name, IntFormat format, int exampleValue);

        Builder<T> withBool(String name, boolean exampleValue);

        Builder<T> withDouble(String name, double exampleValue);

        Builder<T> withString(String name, String exampleValue);

        <E extends Enum<E>> Builder<T> withEnum(String name, E exampleValue);

        Builder<T> withOptionalInt(String name, IntFormat format, int defaultValue, int exampleValue);

        Builder<T> withOptionalBool(String name, boolean defaultValue, boolean exampleVaule);

        Builder<T> withOptionalDouble(String name, double defaultValue, double exampleValue);

        Builder<T> withOptionalString(String name, String defaultValue, String exampleValue);

        <E extends Enum<E>> Builder<T> withOptionalEnum(String name, E defaultValue, E exampleValue);

        IThemeType<T> build();

    }

}
