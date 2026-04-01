plugins {
    alias(libs.plugins.android.test)
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
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
        it.enable = it.buildType == "benchmark"
    }
}
