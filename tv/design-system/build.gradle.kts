plugins {
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "se.yverling.lab.android.tv.design"
}

dependencies {
    implementation(libs.bundles.compose.tv)
}
