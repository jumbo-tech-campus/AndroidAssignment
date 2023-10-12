import dev.sierov.gradle.addKspDependencyForAllTargets
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("dev.sierov.android.library")
    id("dev.sierov.kotlin.multiplatform")
    id("dev.sierov.compose.multiplatform")
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.screen)
                api(projects.feature.root)
                api(projects.feature.start)
                api(projects.feature.products)
                api(projects.api.jumboImpl)
                api(projects.cart.datastoreImpl)
                api(projects.domain.usecase)

                implementation(projects.core)
                implementation(libs.kotlininject.runtime)
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
        targets.withType<KotlinNativeTarget>().configureEach {
            binaries.withType<Framework> {
                isStatic = true
                baseName = "shared"
                export(projects.feature.root)
                export(projects.api.public)
            }
        }
    }
}

android {
    namespace = "dev.sierov.shared"
}

ksp {
    arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)