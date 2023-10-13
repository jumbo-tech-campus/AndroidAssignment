plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    id("dev.sierov.compose.multiplatform")
}

android {
    namespace = "dev.sierov.feature.start"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(projects.screen)
                implementation(projects.cart.public)
                implementation(libs.circuit.foundation)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }
    }
}