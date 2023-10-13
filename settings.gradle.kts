rootProject.name = "JumboApp"

pluginManagement {
    // building our convention plugins
    includeBuild("gradle/build-logic")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        // Used for snapshots if needed
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.7.0")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        // Used for snapshots if needed
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":androidApp",
    ":shared",
    ":core",
    ":screen",
    ":api:public",
    ":api:jumbo-impl",
    ":cart:public",
    ":cart:datastore-impl",
    ":domain:model",
    ":domain:usecase",
    ":feature:root",
    ":feature:start",
    ":feature:products",
)