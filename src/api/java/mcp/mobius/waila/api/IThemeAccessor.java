package mcp.mobius.waila.api;

import java.nio.file.Path;

import mcp.mobius.waila.api.__internal__.ApiSide;
import org.jetbrains.annotations.ApiStatus;

@ApiSide.ClientOnly
@ApiStatus.Experimental
@ApiStatus.NonExtendable
public interface IThemeAccessor {

    Path getPath(String path);

}
