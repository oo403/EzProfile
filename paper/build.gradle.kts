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
    maven("https://repo.extendedclip.com/releases/")
    maven("https://repo.hibiscusmc.com/releases/")
    maven("https://mvn.wesjd.net/")
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.jetbrains.kotlin:kotlin-stdlib:2.3.10")

    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:6.1.0-beta.1")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:6.1.0-beta.1")

    implementation("dev.rollczi:litecommands-bukkit:3.10.2")
    implementation("dev.rollczi:litecommands-adventure-platform:3.10.2")

    implementation("net.wesjd:anvilgui:1.10.11-SNAPSHOT")
    implementation("dev.triumphteam:triumph-gui:3.1.13")

    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("com.hibiscusmc:HMCCosmetics:2.9.0")

    compileOnly("org.mariadb.jdbc:mariadb-java-client:3.5.7")
    compileOnly("com.h2database:h2:2.4.240")
    compileOnly("com.zaxxer:HikariCP:7.0.2")
    compileOnly("mysql:mysql-connector-java:8.0.33")
    compileOnly("org.postgresql:postgresql:42.7.10")
}

val targetJavaVersion = 21

kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {
    shadowJar {
        archiveFileName.set("EzProfile-${project.version}.jar")
        destinationDirectory.set(file("$buildDir/libs"))

        relocate("eu.okaeri.configs", "org.sirox.libs.ezprofile.configs")
        relocate("dev.rollczi.litecommands", "org.sirox.libs.ezprofile.commands")
        relocate("net.wesjd.anvilgui", "org.sirox.libs.ezprofile.anvilgui")
        relocate("dev.triumphteam.gui", "org.sirox.libs.ezprofile.gui")
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