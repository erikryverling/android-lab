package se.yverling.lab.android.convention.plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import se.yverling.lab.android.convention.implementation
import se.yverling.lab.android.convention.libs

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.convention.android.library.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
            }

            compose {
                buildFeatures.compose = true
            }

            android {
                // Run with ./gradlew assembleDebug -PandroidLab.enableComposeCompilerReports=true
                // See: https://chrisbanes.me/posts/composable-metrics for more information
                tasks.withType<KotlinJvmCompile>().configureEach {
                    compilerOptions {
                        if (project.findProperty("androidLab.enableComposeCompilerReports") == "true") {
                            val reports = "-P plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${layout.buildDirectory.get()}/compose_metrics"
                            val metric = "-P plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${layout.buildDirectory.get()}/compose_metrics"

                            freeCompilerArgs.addAll(metric, reports)
                        }
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

private fun Project.compose(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)
