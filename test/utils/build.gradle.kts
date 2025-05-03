plugins {
    alias(libs.plugins.convention.android.library)
}

dependencies {
    implementation(libs.bundles.unitTest)
    implementation(libs.androidTest.runner)
    implementation(libs.androidTest.hilt)
}

android {
    namespace = "se.yverling.lab.android.test"
}
