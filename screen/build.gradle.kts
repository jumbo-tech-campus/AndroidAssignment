plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "dev.sierov.screen"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(libs.circuit.foundation)
            }
        }
    }
}