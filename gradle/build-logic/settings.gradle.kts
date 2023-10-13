dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

include(":convention")