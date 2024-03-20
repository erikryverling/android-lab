plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlinx.kover")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
    namespace = "se.yverling.lab.android.feature.misc"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlinCompiler.get()
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":data:misc"))


    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.coil.compose)

    implementation(libs.timber)

    testImplementation(libs.bundles.unitTest)
    testRuntimeOnly(libs.unitTest.jupiter.engine)

    testImplementation(project(":test:utils"))
}

tasks.register<GreetTask>("greet")
