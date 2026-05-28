plugins {
    alias(libs.plugins.convention.application)
}

android {
    namespace = "se.yverling.lab.android.tv.app"

    defaultConfig {
        applicationId = "se.yverling.lab.android.tv"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.tv)
    implementation(libs.navigation.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material)

    implementation(libs.timber)
}
