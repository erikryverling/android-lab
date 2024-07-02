plugins {
    id("androidlab.hilt-library-conventions")
    id("androidlab.compose-library-conventions")
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:ui"))
    implementation(project(":data:misc"))

    implementation(libs.hilt.navigation)
}

android {
    namespace = "se.yverling.lab.android.feature.animation"
}

// https://developer.android.com/develop/ui/compose/performance/stability/strongskipping
// TODO If we have problem disable this
composeCompiler {
    enableStrongSkippingMode = true
}
