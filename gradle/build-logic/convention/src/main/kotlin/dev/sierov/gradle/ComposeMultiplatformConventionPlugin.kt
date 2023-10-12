package dev.sierov.gradle

import dev.sierov.gradle.internal.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.compose")
        }
        configureCompose()
    }
}