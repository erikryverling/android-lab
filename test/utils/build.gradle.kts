plugins {
    id("androidlab.android-library-conventions")
}

dependencies {
    implementation(libs.bundles.unitTest)
    implementation(libs.androidTest.runner)
    implementation(libs.androidTest.hilt)
}

android {
    namespace = "se.yverling.lab.android.test"
}
