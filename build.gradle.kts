plugins {
    kotlin("jvm") version "1.7.10"
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "de.daniel"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:1.19.0")
}

tasks {
    build {
        dependsOn(reobfJar)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    runServer {
        minecraftVersion("1.19.2")
    }
}

bukkit {
    name = "projectile-trajectories"
    apiVersion = "1.19"
    main = "$group.trajectories.InternalMainClass"
    version = getVersion().toString()
    libraries = listOf(
        "net.axay:kspigot:1.19.0"
    )
}