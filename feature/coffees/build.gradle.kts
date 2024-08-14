plugins {
    id("androidlab.compose-library-conventions")
    id("androidlab.hilt-library-conventions")
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":common:model"))
    implementation(project(":data:coffees"))

    implementation(libs.hilt.navigation)

    implementation(libs.compose.constraintlayout)

    implementation(libs.bundles.navigation)

    kspAndroidTest(libs.androidTest.hilt.compiler)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(project(":test:utils"))

    debugImplementation(libs.androidTest.compose.manifest)
}

android {
    namespace = "se.yverling.lab.android.feature.coffees"

    defaultConfig {
        testInstrumentationRunner = "se.yverling.lab.android.test.AndroidLabTestRunner"
    }
}
