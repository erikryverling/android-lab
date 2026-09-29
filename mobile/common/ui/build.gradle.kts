plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "se.yverling.lab.android.ui"

    testOptions {
        screenshotTests.create("screenshotTest") {
            engineVersion = "0.0.1-alpha16"
            targetVariants.add("debug")

            dependencies {
                implementation(libs.compose.tooling)
                implementation(libs.screenshot.validation.api)
            }
        }
    }
}

dependencies {
    implementation(projects.mobile.common.designSystem)
    implementation(projects.mobile.common.model)
}
