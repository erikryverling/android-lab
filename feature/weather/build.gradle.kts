plugins {
    id("androidlab.hilt-library-conventions")
    id("androidlab.compose-library-conventions")
    alias(libs.plugins.screenshot)
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":data:weather"))

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

    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}
