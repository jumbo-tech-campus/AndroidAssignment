import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    id("dev.sierov.compose.multiplatform")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.activity.activity)
                api(libs.androidx.appcompat)
                api(libs.androidx.core)
            }
        }
        targets.withType<KotlinNativeTarget>().configureEach {
            binaries.withType<Framework> {
                isStatic = true
                baseName = "shared"
                // export(projects.feature.root)
            }
        }
    }
}

android {
    namespace = "dev.sierov.shared"
}