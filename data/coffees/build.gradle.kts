plugins {
    id("androidlab.hilt-library-conventions")
    alias(libs.plugins.serialization)
}

dependencies {
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.kotlinx.serialization)
}

android {
    namespace = "se.yverling.lab.android.data.coffees"
}
