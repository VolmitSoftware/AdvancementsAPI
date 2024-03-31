import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.5.11" apply false
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

class NMSVersion(val nmsVersion: String, val serverVersion: String)
infix fun String.toNms(that: String): NMSVersion = NMSVersion(this, that)
val SUPPORTED_VERSIONS: List<NMSVersion> = listOf(
/*        "v1_19_R1" toNms "1.19.2-R0.1-SNAPSHOT",
        "v1_19_R2" toNms "1.19.3-R0.1-SNAPSHOT",
        "v1_19_R3" toNms "1.19.4-R0.1-SNAPSHOT",
        "v1_20_R1" toNms "1.20.1-R0.1-SNAPSHOT",
        "v1_20_R2" toNms "1.20.2-R0.1-SNAPSHOT", */
        "v1_20_R3" toNms "1.20.4-R0.1-SNAPSHOT"
)

SUPPORTED_VERSIONS.forEach {
    project(":nms:${it.nmsVersion}") {
        apply(plugin = "java")
        apply(plugin = "io.papermc.paperweight.userdev")

        dependencies {
            compileOnly("io.papermc.paper:paper-api:${it.serverVersion}")
            implementation(project(":core"))
            paperDevBundle(it.serverVersion)
        }
    }
}

group = "eu.endercentral.crazy_advancements"
version = "2.1.17"

allprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
            dependsOn(clean)
        }
    }
}

dependencies {
    implementation(project(":core"))
    SUPPORTED_VERSIONS.forEach {
        implementation(project(path = ":nms:${it.nmsVersion}", configuration = "reobf"))
    }
}

tasks {
    shadowJar {
        SUPPORTED_VERSIONS.forEach{dependsOn(":nms:${it.nmsVersion}:reobfJar") }
        archiveClassifier.set("")
    }

    build.get().dependsOn(shadowJar)
}

tasks.register<ShadowJar>("shadable") {
    dependsOn(tasks.build)
    SUPPORTED_VERSIONS.forEach{dependsOn(":nms:${it.nmsVersion}:reobfJar") }
    configurations = tasks.shadowJar.get().configurations
    archiveClassifier.set("shadable")

    exclude("plugin.yml")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks.shadowJar)
            artifact(tasks.getByName("shadable"))
        }
    }
}

bukkit {
    main = "eu.endercentral.crazy_advancements.CrazyAdvancementsAPI"
    author = "ZockerAxel"
    apiVersion = "1.20" // Should be always same as dev bundle version (without minor versions)
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    commands {
        register("grant") {
            usage = "/grant <Player> <Manager> <Advancement> [Criteria...]"
            description = "Grants <Advancement>-[Criteria...] to <Player> in <Manager>"
            aliases = listOf("cagrant")
        }

        register("revoke") {
            usage = "/revoke <Player> <Manager> <Advancement> [Criteria...]"
            description = "Revokes <Advancement>-[Criteria...] to <Player> in <Manager>"
            aliases = listOf("carevoke")
        }

        register("setprogress") {
            usage = "/setprogress <Player> <Manager> <Advancement> <Number> [Operation]"
            description = "Sets <Advancement> Progress for <Player> in <Manager> using [Operation]"
            aliases = listOf("caprogress")
        }

        register("showtoast") {
            usage = "/showtoast <Player> <Icon> [Frame] <Message>"
            description = "Displays a Toast Advancement Message"
            aliases = listOf("catoast", "toast")
        }

        register("careload") {
            usage = "/careload [Category]"
            description = "Reloads the Crazy Advancements API. Valid categories are all, advancements, items"
        }
    }
}