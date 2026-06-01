plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "se.yverling.lab.android.tv.ui"
}

dependencies {
    implementation(libs.bundles.compose.tv)
}
