plugins {
    kotlin("jvm") version "2.3.20-RC"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "org.sirox"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.okaeri.cloud/releases")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.jetbrains.kotlin:kotlin-stdlib:2.3.10")

    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:6.1.0-beta.1")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:6.1.0-beta.1")
    implementation("dev.rollczi:litecommands-bukkit:3.10.2")
}

val targetJavaVersion = 21

kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        destinationDirectory.set(file("$buildDir/libs"))
    }

    runServer {
        minecraftVersion("1.21.11")
        dependsOn(shadowJar)

        doFirst {
            val kotlinStdlib = configurations.runtimeClasspath.get()
                .filter { it.name.startsWith("kotlin-stdlib") }
                .joinToString(";") { it.absolutePath }
            jvmArgs("-Xbootclasspath/a:$kotlinStdlib")

            val debugAgent = file("${rootProject.buildDir}/libs/kotlinx-coroutines-debug.jar")
            if (debugAgent.exists()) {
                jvmArgs("-javaagent:${debugAgent.absolutePath}")
            }
        }
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}