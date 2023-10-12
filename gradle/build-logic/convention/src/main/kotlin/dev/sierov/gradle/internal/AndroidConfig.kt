package dev.sierov.gradle.internal

import com.android.build.gradle.BaseExtension
import dev.sierov.gradle.AndroidVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid() {
    android {
        compileSdkVersion(AndroidVersion.compileSdk)

        defaultConfig {
            minSdk = AndroidVersion.minSdk
            targetSdk = AndroidVersion.targetSdk
        }

        compileOptions {
            // https://developer.android.com/studio/write/java8-support
            isCoreLibraryDesugaringEnabled = true
        }

        testOptions {
            unitTests {
                isReturnDefaultValues = true
            }
        }
    }

    dependencies {
        // https://developer.android.com/studio/write/java8-support
        "coreLibraryDesugaring"(libs.findLibrary("tools.desugarjdklibs").get())
    }
}

internal fun Project.android(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)
