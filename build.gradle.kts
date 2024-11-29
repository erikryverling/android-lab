plugins {
    // NOTE! There are more plugins defined in buildSrc/build.gradle.kts
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.serialization) apply false
}
