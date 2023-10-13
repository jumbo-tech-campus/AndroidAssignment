plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "dev.sierov.core"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlin.coroutines.core)
                api(libs.kotlininject.runtime)
                api(libs.kermit.kermit)
            }
        }
    }
}