plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
}

android {
    namespace = "dev.sierov.api"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.domain.model)
            }
        }
    }
}