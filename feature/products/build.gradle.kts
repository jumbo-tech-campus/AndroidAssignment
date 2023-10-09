plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    id("dev.sierov.compose.multiplatform")
}

android {
    namespace = "dev.sierov.feature.products"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.circuit.foundation)
                implementation(libs.circuit.runtime)
                implementation(libs.circuit.retained)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.animation)
            }
        }
    }
}