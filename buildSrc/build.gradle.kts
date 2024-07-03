plugins {
    alias(libs.plugins.kotlin.dsl)
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // NOTE! This will put the Gradle plugins on the classpath of the (whole) project
    implementation(libs.android.gradlePlugin)
    implementation(libs.javapoet)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.parcelize.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.hilt.gradlePlugin)
    implementation(libs.kover.gradlePlugin)
    implementation(libs.compose.compiler.gradlePlugin)
}
