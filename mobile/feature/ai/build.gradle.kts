plugins {
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.compose)
}

dependencies {
    implementation(projects.mobile.common.designSystem)
    implementation(projects.mobile.common.ui)
    implementation(projects.mobile.common.model)
    implementation(projects.mobile.data.ai)

    implementation(libs.hilt.navigation)
}

android {
    namespace = "se.yverling.lab.android.feature.ai"
}
