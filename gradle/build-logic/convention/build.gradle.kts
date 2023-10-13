plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dev.sierov.android.application"
            implementationClass = "dev.sierov.gradle.AndroidApplicationConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "dev.sierov.kotlin.multiplatform"
            implementationClass = "dev.sierov.gradle.KotlinMultiplatformConventionPlugin"
        }
        register("kotlinAndroid") {
            id = "dev.sierov.kotlin.android"
            implementationClass = "dev.sierov.gradle.KotlinAndroidConventionPlugin"
        }
        register("androidLibrary") {
            id = "dev.sierov.android.library"
            implementationClass = "dev.sierov.gradle.AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "dev.sierov.compose.multiplatform"
            implementationClass = "dev.sierov.gradle.ComposeMultiplatformConventionPlugin"
        }
    }
}