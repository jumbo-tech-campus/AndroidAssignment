plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
}

android {
    namespace = "dev.sierov.cart"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
            }
        }
    }
}