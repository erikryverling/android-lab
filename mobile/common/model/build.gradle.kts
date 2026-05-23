plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization)
}

android {
    namespace = "se.yverling.lab.android.model"
}
