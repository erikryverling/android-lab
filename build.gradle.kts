import java.util.*

plugins {
    // NOTE! There are more plugins defined in buildSrc/build.gradle.kts
    alias(libs.plugins.versions)
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.serialization) apply false
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.getDefault()).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
