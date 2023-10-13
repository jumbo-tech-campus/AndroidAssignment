plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.sierov.api.jumbo"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.api.public)
                api(libs.kotlinx.serialization)
                api(libs.ktor.client.core)
                implementation(projects.core)
                implementation(libs.kotlininject.runtime)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.client.logging)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}