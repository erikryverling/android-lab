plugins {
    id("androidlab.hilt-library-conventions")
    id("androidlab.compose-library-conventions")
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":data:weather"))

    implementation(libs.hilt.navigation)
}

android {
    namespace = "se.yverling.lab.android.feature.weather"
}
