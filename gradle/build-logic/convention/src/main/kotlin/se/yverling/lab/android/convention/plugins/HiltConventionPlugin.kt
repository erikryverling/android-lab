package se.yverling.lab.android.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import se.yverling.lab.android.convention.implementation
import se.yverling.lab.android.convention.ksp
import se.yverling.lab.android.convention.libs

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.convention.android.library.get().pluginId)
                apply(libs.plugins.ksp.get().pluginId)
                apply(libs.plugins.hilt.android.get().pluginId)
            }

            dependencies {
                ksp(libs.hilt.android.compiler)
                implementation(libs.hilt.android)
            }
        }
    }
}
