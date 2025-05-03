plugins {
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.compose)
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.ui)
    implementation(projects.common.model)
    implementation(projects.data.ai)

    implementation(libs.hilt.navigation)
}

android {
    namespace = "se.yverling.lab.android.feature.ai"
}
