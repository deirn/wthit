# Getting Started

## Gradle Setup
To get started making a WTHIT plugin, add the following to your `build.gradle`

### Adding Repository
```groovy
repositories {
  maven { url "https://maven.bai.lol" }
}
```

### Declaring Dependencies
=== "Fabric"
    ```gradle
    dependencies {
      // compile against the API
      modCompileOnly "mcp.mobius.waila:wthit-api:fabric-${wthitVersion}"

      // run against the full jar
      modRuntimeOnly "mcp.mobius.waila:wthit:fabric-${wthitVersion}"
      modRuntimeOnly "lol.bai:badpackets:fabric-0.2.0"
    }
    ```
=== "Forge"
    ```gradle 
    buildscript {
      dependencies {
        classpath "org.spongepowered:mixingradle:0.7.+"
      }
    }

    apply plugin: "org.spongepowered.mixin"
    
    dependencies {
      // compile against the API
      compileOnly fg.deobf("mcp.mobius.waila:wthit-api:forge-${wthitVersion}")

      // run against the full jar
      runtimeOnly fg.deobf("mcp.mobius.waila:wthit:forge-${wthitVersion}")
      runtimeOnly fg.deobf("lol.bai:badpackets:forge-0.2.0")
    }
    ```
=== "Quilt"
    ```gradle
    dependencies {
      // compile against the API
      modCompileOnly "mcp.mobius.waila:wthit-api:quilt-${wthitVersion}"

      // run against the full jar
      modRuntimeOnly "mcp.mobius.waila:wthit:quilt-${wthitVersion}"
      modRuntimeOnly "lol.bai:badpackets:fabric-0.2.0"
    }
    ```
=== "Architectury"
    ```gradle title="Common Project"
    dependencies {
      modCompileOnly "mcp.mobius.waila:wthit-api:fabric-${wthitVersion}"
    }
    ```
    ```gradle title="Fabric Project"
    dependencies {
      modRuntimeOnly "mcp.mobius.waila:wthit:fabric-${wthitVersion}"
      modRuntimeOnly "lol.bai:badpackets:fabric-0.2.0"
    }
    ```
    ```gradle title="Forge Project"
    dependencies {
      // needed for @WailaPlugin annotation
      modCompileOnly "mcp.mobius.waila:wthit-api:forge-${wthitVersion}"
      modRuntimeOnly "mcp.mobius.waila:wthit:forge-${wthitVersion}"
      modRuntimeOnly "lol.bai:badpackets:forge-0.2.0"
    }
    ```
=== "VanillaGradle Multiplatform"
    ```gradle title="Common Project"
    dependencies {
      compileOnly "mcp.mobius.waila:wthit-api:mojmap-${wthitVersion}"
    }
    ```
    ```gradle title="Fabric Project"
    dependencies {
      modRuntimeOnly "mcp.mobius.waila:wthit:fabric-${wthitVersion}"
      modRuntimeOnly "lol.bai:badpackets:fabric-0.2.0"
    }
    ```
    ```gradle title="Forge Project"
    buildscript {
      dependencies {
        classpath "org.spongepowered:mixingradle:0.7.+"
      }
    }

    apply plugin: "org.spongepowered.mixin"
    
    dependencies {
      // needed for @WailaPlugin annotation
      compileOnly fg.deobf("mcp.mobius.waila:wthit-api:forge-${wthitVersion}")
      runtimeOnly fg.deobf("mcp.mobius.waila:wthit:forge-${wthitVersion}")
      runtimeOnly fg.deobf("lol.bai:badpackets:forge-0.2.0")
    }
    ```
    ```gradle title="Quilt Project"
    dependencies {
      modRuntimeOnly "mcp.mobius.waila:wthit:quilt-${wthitVersion}"
      modRuntimeOnly "lol.bai:badpackets:fabric-0.2.0"
    }
    ```

???+ note "Why compiling against the API jar?"
     When you compile against the full jar and use non API classes, your mod could break any time WTHIT updates.
     On the other hand, the API jar is guaranteed to be stable. No breaking changes without deprecation time.

     If you found yourself needing to touch non API classes, [open an issue on GitHub](https://github.com/badasintended/wthit/issues/new?assignees=&labels=api&template=api.md&title=).

??? note "Available packages"
    All packages has `mcp.mobius.waila` as their group.

    | Package                       | Description                                                                                   |
    |:------------------------------|:----------------------------------------------------------------------------------------------|
    | `wthit-api:fabric-${version}` | Intermediary API jar for Loom projects                                                        |
    | `wthit-api:forge-${version}`  | SRG API jar for ForgeGradle projects                                                          |
    | `wthit-api:quilt-${version}`  | Currently intermediary API jar, would be hashed-mojmap when Quilt supports building it        |
    | `wthit-api:mojmap-${version}` | Mojang Mappings API jar for VanillaGradle projects                                            |
    | `wthit:fabric-${version}`     | Full runtime jar for Fabric                                                                   |
    | `wthit:forge-${version}`      | Full runtime jar for Forge                                                                    |
    | `wthit:quilt-${version}`      | Full runtime jar for Quilt                                                                    |
    | `wthit:mojmap-${version}`     | Full platform independent jar for strange people that need access to internal implementations |
    

## Creating Plugins

### Making a Plugin Class
Make a class that implements `IWailaPlugin`
```java
public class MyWailaPlugin implements IWailaPlugin {
  @Override
  public void register(IRegistrar registrar) {
      // register your component here
  }
}
```


### Registering Plugins
Create a file called `waila_plugins.json` in the root of your mod, commonly in `src/main/resources` folder on your project.
```json
{
  // the plugin identifier, [namespace:path]
  "yourmodid:plugin": {
    // the path to the implementation class
    "initializer": "package.YourWailaPlugin",

    // optional, decide the environment the plugin will loaded, options:
    // client    load plugin only on client and integrated server
    // server    load plugin only on dedicated server
    // *         load plugin on both client and dedicated server
    "side": "*",

    // optional, the required mods that this plugin needs
    "required": ["othermodid", "anotherone"]
  },

  // register multiple plugins!
  "yourmodid:another": { /*...*/ }
}
```
