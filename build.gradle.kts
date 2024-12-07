plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.5"
    id("io.papermc.paperweight.userdev") version "1.7.5"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
}

group = "com.github.stefvanschie.inventoryframework"
version = "0.11.0-SNAPSHOT"
description = "IF"
java.sourceCompatibility = JavaVersion.VERSION_21

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}