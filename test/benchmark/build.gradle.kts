plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
}

dependencies {
    implementation(libs.unitTest.junit4)
    implementation(libs.benchmark.macro.junit4)
}

android {
    namespace = "se.yverling.lab.android.benchmark"

    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk =  Versions.minSdk
    }

    compileOptions {
        // KSP only supports Java 17
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }

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
    @Suppress("UnstableApiUsage")
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

androidComponents {
    beforeVariants(selector().all()) {
        @Suppress("DEPRECATION")
        it.enabled = it.buildType == "benchmark"
    }
}
