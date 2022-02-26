import java.nio.charset.StandardCharsets

plugins {
    java
    id("org.spongepowered.gradle.vanilla")
    id("maven-publish")
}

version = env["MOD_VERSION"] ?: "${prop["majorVersion"]}.999-${env["GIT_HASH"] ?: "local"}"

allprojects {
    apply(plugin = "base")
    apply(plugin = "java")

    version = rootProject.version

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = StandardCharsets.UTF_8.name()
        options.release.set(17)
    }
}

subprojects {
    base {
        archivesName.set("${rootProject.base.archivesName.get()}-${project.name}")
    }
}

minecraft {
    version(rootProp["minecraft"])
    accessWideners(project(":fabric").file("src/main/resources/wthit.accesswidener"))
}

sourceSets {
    val main by getting
    val api by creating
    val impl by creating
    val pluginCore by creating
    val pluginVanilla by creating
    val pluginTest by creating

    listOf(api, impl, pluginCore, pluginVanilla, pluginTest).applyEach {
        compileClasspath += main.compileClasspath
    }
    listOf(api, main).applyEach {
        compileClasspath += impl.output
    }
    listOf(main, pluginCore, pluginVanilla, pluginTest).applyEach {
        compileClasspath += api.output
    }
}

val apiJar = task<Jar>("apiJar") {
    archiveClassifier.set("api")
    from(sourceSets["api"].output)
}

val apiSourcesJar = task<Jar>("apiSourcesJar") {
    archiveClassifier.set("api-sources")
    from(sourceSets["api"].allSource)
}

publishing {
    repositories {
        gitlabMaven()
    }

    publications {
        create<MavenPublication>("apiMojmap") {
            artifactId = "${rootProp["archiveBaseName"]}-api"
            version = "mojmap-${project.version}"
            artifact(apiJar) {
                classifier = null
            }
            artifact(apiSourcesJar) {
                classifier = "sources"
            }
        }
    }
}
