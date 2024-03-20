import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("androidlab.android-library-conventions")
}

dependencies {
    implementation(platform(libsx.composeBom))
    implementation(libsx.bundleCompose)
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
    }
}

// Run with ./gradlew assembleDebug -PandroidLab.enableComposeCompilerReports=true
// See: https://chrisbanes.me/posts/composable-metrics for more information
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
