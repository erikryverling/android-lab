plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

dependencies {
    implementation(projects.mobile.common.designSystem)
    implementation(projects.mobile.common.ui)
    implementation(projects.mobile.common.model)
    implementation(projects.mobile.data.coffees)

    implementation(libs.hilt.navigation)

    implementation(libs.compose.constraintlayout)

    implementation(libs.bundles.navigation)

    kspAndroidTest(libs.androidTest.hilt.compiler)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(projects.mobile.test.utils)

    debugImplementation(libs.androidTest.compose.manifest)
}

android {
    namespace = "se.yverling.lab.android.feature.coffees"

    defaultConfig {
        testInstrumentationRunner = "se.yverling.lab.android.test.AndroidLabTestRunner"
    }

    packaging {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }
}
