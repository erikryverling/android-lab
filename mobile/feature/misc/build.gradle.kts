plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

dependencies {
    implementation(projects.mobile.common.designSystem)
    implementation(projects.mobile.common.ui)
    implementation(projects.mobile.data.misc)

    implementation(libs.hilt.navigation)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}

android {
    namespace = "se.yverling.lab.android.feature.misc"
}
