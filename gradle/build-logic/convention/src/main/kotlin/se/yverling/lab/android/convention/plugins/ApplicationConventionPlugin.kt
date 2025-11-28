package se.yverling.lab.android.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import se.yverling.lab.android.convention.alias
import se.yverling.lab.android.convention.libs

class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                alias(libs.plugins.android.application)
                alias(libs.plugins.kotlin.android)
                alias(libs.plugins.ksp)
                alias(libs.plugins.hilt.android)
                alias(libs.plugins.kotlin.compose)
            }

            android()
        }
    }
}
