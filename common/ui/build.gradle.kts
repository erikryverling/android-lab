plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "se.yverling.lab.android.ui"
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.model)
}
