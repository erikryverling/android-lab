import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.versions)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.serialization) apply false
}

// Run with ./gradlew dependencyUpdates
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.getDefault()).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

// Run with /gradlew assembleDebug -PandroidLab.enableComposeCompilerReports=true
// See: https://chrisbanes.me/posts/composable-metrics for more information
subprojects {
    tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            if (project.findProperty("androidLab.enableComposeCompilerReports") == "true") {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            layout.buildDirectory.get() + "/compose_metrics"
                )

                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            layout.buildDirectory.get() + "/compose_metrics"
                )
            }
        }
    }
}
