package se.yverling.lab.android.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import se.yverling.lab.android.convention.implementation
import se.yverling.lab.android.convention.libs
import se.yverling.lab.android.convention.testImplementation
import se.yverling.lab.android.convention.testRuntimeOnly

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.kotlin.android.get().pluginId)
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kover.get().pluginId)
            }

            configureAndroidBase()
            configureAndroidJunit5()

            dependencies {
                implementation(libs.timber)

                testImplementation(libs.bundles.unitTest)
                testImplementation(project(":test:utils"))
                testRuntimeOnly(libs.unitTest.junit.platformLauncher)
            }
        }
    }
}
