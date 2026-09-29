plugins {
    alias(libs.plugins.convention.hilt)
}

dependencies {
    implementation(projects.mobile.common.model)

    implementation(libs.kotlinx.serialization)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.ai)
}

android {
    namespace = "se.yverling.lab.android.data.ai"
}
