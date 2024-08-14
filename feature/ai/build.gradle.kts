plugins {
    id("androidlab.hilt-library-conventions")
    id("androidlab.compose-library-conventions")
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":common:model"))
    implementation(project(":data:ai"))

    implementation(libs.hilt.navigation)
}

android {
    namespace = "se.yverling.lab.android.feature.ai"
}
