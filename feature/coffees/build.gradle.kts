plugins {
    alias(libs.plugins.convention.compose)
    alias(libs.plugins.convention.hilt)
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.ui)
    implementation(projects.common.model)
    implementation(projects.data.coffees)

    implementation(libs.hilt.navigation)

    implementation(libs.compose.constraintlayout)

    implementation(libs.bundles.navigation)

    kspAndroidTest(libs.androidTest.hilt.compiler)
    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(projects.test.utils)

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
