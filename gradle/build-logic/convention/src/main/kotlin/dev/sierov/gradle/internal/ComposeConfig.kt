package dev.sierov.gradle.internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension

internal fun Project.configureCompose() {
    with(extensions.getByType<ComposeExtension>()) {
        // add configuration here
    }
}
