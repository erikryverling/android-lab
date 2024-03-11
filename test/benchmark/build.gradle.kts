plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
    namespace = "se.yverling.lab.android.benchmark"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks += listOf("release")
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.unitTest.junit4)
    implementation(libs.benchmark.macro.junit4)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enabled = it.buildType == "benchmark"
    }
}
