plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.12"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

group = "com.github.stefvanschie.inventoryframework"
version = "0.11.1-SNAPSHOT"
description = "IF"
java.sourceCompatibility = JavaVersion.VERSION_21

publishing {
    repositories {
        maven {
            name = "gahvila"
            url = uri("https://repo.gahvila.net/snapshots/")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.stefvanschie"
            artifactId = "inventoryframework"
            version = findProperty("version").toString()
            from(components["java"])
        }
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