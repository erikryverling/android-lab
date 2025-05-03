package se.yverling.lab.android.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import se.yverling.lab.android.convention.libs

class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
                apply(libs.plugins.hilt.android.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
            }

            configureAndroidBase()
        }
    }
}
