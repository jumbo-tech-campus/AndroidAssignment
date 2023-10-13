package dev.sierov.gradle

import dev.sierov.gradle.internal.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.gradle.android.cache-fix")
            }

            configureAndroid()
        }
    }
}
