plugins {
    alias(libs.plugins.convention.application)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.kover)
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.model)
    implementation(projects.feature.coffees)
    implementation(projects.feature.weather)
    implementation(projects.feature.ai)
    implementation(projects.feature.misc)

    implementation(libs.appCompat)

    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation(libs.bundles.navigation)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.timber)

    implementation(libs.profileinstaller)

    implementation(libs.work)

    implementation(libs.material3.windowSizeClassAndroid)

    testImplementation(libs.unitTest.junit4)
    testImplementation(projects.data.coffees)

    androidTestImplementation(libs.androidTest.runner)
    androidTestImplementation(libs.androidTest.hilt)
    androidTestImplementation(libs.androidTest.work)
    androidTestImplementation(libs.unitTest.coroutines)
    androidTestImplementation(libs.unitTest.kotest.assertions)
}

android {
    namespace = "se.yverling.lab.android"

    defaultConfig {
        minSdk = Versions.minSdk

        applicationId = "se.yverling.lab.android"

        versionCode = 10000 // Version & release number
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    lint {
        // This will generate a single report for all modules
        checkDependencies = true
        warningsAsErrors = true
        disable += listOf("IconLocation")
        xmlReport = false
        htmlReport = true
        htmlOutput = file("${project.rootDir}/build/reports/android-lint.html")
    }
}

plugins.withId(libs.plugins.paparazzi.get().pluginId) {
    // Defer until afterEvaluate so that testImplementation is created by Android plugin.
    afterEvaluate {
        dependencies.constraints {
            add("testImplementation", "com.google.guava:guava") {
                attributes {
                    attribute(
                        TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE,
                        objects.named(TargetJvmEnvironment::class.java, TargetJvmEnvironment.STANDARD_JVM)
                    )
                }
                because(
                    "LayoutLib and sdk-common depend on Guava's -jre published variant." +
                            "See https://github.com/cashapp/paparazzi/issues/906."
                )
            }
        }
    }
}

// Use for creating an aggregated Kover report
dependencies {
    kover(projects.feature.coffees)
    kover(projects.feature.weather)
    kover(projects.feature.misc)
    kover(projects.data.coffees)
    kover(projects.data.weather)
    kover(projects.data.misc)
}

kover {
    reports {
        filters {
            includes {
                classes("*ViewModel*", "*UseCase*", "*Repository*")
            }

            excludes {
                classes("hilt_*", "*_Factory*", "*_Hilt*")
            }
        }
    }
}
