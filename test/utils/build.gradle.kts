plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
    namespace = "se.yverling.lab.android.test"

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
    }
}



dependencies {
    implementation(libs.bundles.unitTest)
    implementation(libs.androidTest.runner)
    implementation(libs.androidTest.hilt)
}
