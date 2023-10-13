plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
}

android {
    namespace = "dev.sierov.domain.usecase"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.domain.model)
                api(projects.api.public)
                implementation(projects.core)
                implementation(libs.kotlinx.atomicfu)
            }
        }
    }
}