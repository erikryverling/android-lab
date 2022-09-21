allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.test).apply(false)
    alias(libs.plugins.android.kotlin).apply(false)
    alias(libs.plugins.hilt).apply(false)
    alias(libs.plugins.protobuf).apply(false)
    alias(libs.plugins.versions)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.serialization).apply(false)
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
