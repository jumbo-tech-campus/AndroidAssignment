plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    id("dev.sierov.compose.multiplatform")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.sierov.domain.model"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(libs.kotlinx.serialization)
                implementation(compose.runtime)
            }
        }
    }
}