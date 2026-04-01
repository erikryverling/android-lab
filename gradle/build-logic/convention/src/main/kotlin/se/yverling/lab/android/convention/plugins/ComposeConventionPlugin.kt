package se.yverling.lab.android.convention.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import se.yverling.lab.android.convention.alias
import se.yverling.lab.android.convention.android
import se.yverling.lab.android.convention.implementation
import se.yverling.lab.android.convention.kotlin
import se.yverling.lab.android.convention.libs

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.run {
                alias(libs.plugins.convention.android.library)
                alias(libs.plugins.kotlin.compose)
            }

            compose {
                when (this) {
                    is ApplicationExtension -> buildFeatures { compose = true }
                    is LibraryExtension -> buildFeatures { compose = true }
                }
            }

            kotlin {
                compilerOptions {
                    if (project.findProperty("androidLab.enableComposeCompilerReports") == "true") {
                        val reports = "-P plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${layout.buildDirectory.get()}/compose_metrics"
                        val metric = "-P plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${layout.buildDirectory.get()}/compose_metrics"

                        freeCompilerArgs.addAll(metric, reports)
                    }
                }
            }

            dependencies {
                implementation(platform(libs.compose.bom))
                implementation(libs.bundles.compose)
            }
        }
    }
}

private fun Project.compose(action: CommonExtension.() -> Unit) = android(action)
