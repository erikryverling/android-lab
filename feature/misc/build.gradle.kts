plugins {
    id("androidlab.compose-library-conventions")
    id("androidlab.hilt-library-conventions")
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":data:misc"))

    implementation(libs.hilt.navigation)

    implementation(libs.coil.compose)
}

android {
    namespace = "se.yverling.lab.android.feature.misc"
}

// Just as en example
tasks.register<GreetTask>("greet")
