plugins {
    id("androidlab.compose-library-conventions")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "se.yverling.lab.android.ui"
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":common:model"))
}
