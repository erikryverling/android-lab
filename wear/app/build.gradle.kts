plugins {
    alias(libs.plugins.convention.application)
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.tooling)
    implementation(libs.bundles.compose.wear)
    implementation(libs.timber)

    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
}

android {
    namespace = "se.yverling.lab.android.wear.app"

    defaultConfig {
        applicationId = "se.yverling.lab.android.wear"
        versionCode = 1
        versionName = "1.0"
    }
}
