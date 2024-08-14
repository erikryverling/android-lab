plugins {
    id("androidlab.compose-library-conventions")
    id("org.jetbrains.kotlin.plugin.parcelize")
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization)
}

android {
    namespace = "se.yverling.lab.android.model"
}
