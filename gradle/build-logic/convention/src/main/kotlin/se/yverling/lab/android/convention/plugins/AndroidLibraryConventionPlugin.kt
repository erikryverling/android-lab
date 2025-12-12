package se.yverling.lab.android.convention.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import se.yverling.lab.android.convention.alias
import se.yverling.lab.android.convention.commonAndroidConfig
import se.yverling.lab.android.convention.commonAndroidJunit5
import se.yverling.lab.android.convention.implementation
import se.yverling.lab.android.convention.libs
import se.yverling.lab.android.convention.testImplementation
import se.yverling.lab.android.convention.testRuntimeOnly

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                alias(libs.plugins.kotlin.android)
                alias(libs.plugins.android.library)
                alias(libs.plugins.kover)
            }

            commonAndroidConfig()
            commonAndroidJunit5()

            dependencies {
                implementation(libs.timber)

                testImplementation(libs.bundles.unitTest)
                // TODO Can't use projects.test.utils
                testImplementation(project(":test:utils"))
                testRuntimeOnly(libs.unitTest.junit.platformLauncher)
            }
        }
    }
}
