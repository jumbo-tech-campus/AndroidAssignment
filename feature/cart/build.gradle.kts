plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    id("dev.sierov.compose.multiplatform")
}

android {
    namespace = "dev.sierov.feature.cart"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(projects.screen)
                implementation(libs.circuit.foundation)
                implementation(libs.kotlininject.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }
    }
}