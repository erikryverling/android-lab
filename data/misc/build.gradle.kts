plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
    namespace = "se.yverling.lab.android.data.misc"

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation(libs.timber)

    testImplementation(libs.bundles.unitTest)
    testRuntimeOnly(libs.unitTest.jupiter.engine)
}