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
                implementation(projects.core)
                implementation(projects.screen)
                implementation(projects.cart.public)
                implementation(projects.domain.model)
                implementation(projects.domain.usecase)
                implementation(libs.circuit.runtime)
                implementation(libs.circuit.retained)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.animation)
                implementation(libs.kotlininject.runtime)
                implementation(libs.imageloader)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(projects.cart.datastoreImpl)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.circuit.testing)
                implementation(libs.junit)
                implementation(libs.turbine)
            }
        }
    }
}