plugins {
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.screenshot)
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.ui)
    implementation(projects.data.weather)

    implementation(libs.hilt.navigation)

    // Run with ./gradlew :feature:weather:updateDebugScreenshotTest
    // Test with ./gradlew :feature:weather:validateDebugScreenshotTest
    // Test reports: feature/weather/build/reports/screenshotTest/preview/debug/index.html
    screenshotTestImplementation(libs.compose.tooling)
}

android {
    namespace = "se.yverling.lab.android.feature.weather"

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    @Suppress("UnstableApiUsage")
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}
