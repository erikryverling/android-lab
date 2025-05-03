plugins {
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

dependencies {
    implementation(projects.common.model)

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.kotlinx.serialization)
}

android {
    namespace = "se.yverling.lab.android.data.coffees"
}
