plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
}

android {
    namespace = "dev.sierov.cart.local"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.cart.public)
                implementation(projects.core)
                implementation(libs.kotlininject.runtime)
                implementation(libs.androidx.datastore.preferences)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.datastore.android)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.junit)
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.test.junit)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.turbine)
            }
        }
    }
}