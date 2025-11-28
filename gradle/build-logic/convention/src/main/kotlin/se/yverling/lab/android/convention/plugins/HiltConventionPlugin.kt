package se.yverling.lab.android.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import se.yverling.lab.android.convention.alias
import se.yverling.lab.android.convention.implementation
import se.yverling.lab.android.convention.ksp
import se.yverling.lab.android.convention.libs

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.ksp)
                alias(libs.plugins.hilt.android)
            }

            dependencies {
                ksp(libs.hilt.android.compiler)
                implementation(libs.hilt.android)
            }
        }
    }
}
