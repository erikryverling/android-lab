plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
    namespace = "se.yverling.lab.android.data.coffees"
}

dependencies {
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(libs.kotlinx.serialization)

    testImplementation(libs.bundles.unitTest)
}
