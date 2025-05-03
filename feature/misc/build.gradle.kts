plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.ui)
    implementation(projects.data.misc)

    implementation(libs.hilt.navigation)

    implementation(libs.coil.compose)
}

android {
    namespace = "se.yverling.lab.android.feature.misc"
}
