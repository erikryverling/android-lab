import com.android.build.api.dsl.AgpTestSuiteInputParameters

plugins {
    alias(libs.plugins.convention.application)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.kover)
    alias(libs.plugins.google.services)
}

dependencies {
    implementation(projects.mobile.common.designSystem)
    implementation(projects.mobile.common.model)
    implementation(projects.mobile.feature.coffees)
    implementation(projects.mobile.feature.weather)
    implementation(projects.mobile.feature.ai)
    implementation(projects.mobile.feature.misc)

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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.common.ktx)


    testImplementation(libs.unitTest.junit4)
    testImplementation(projects.mobile.data.coffees)

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
        disable += listOf(
            "IconLocation",
            "AndroidGradlePluginVersion",
            "GradleDependency",
            "NewerVersionAvailable"
        )
        xmlReport = false
        htmlReport = true
        htmlOutput = file("${project.rootDir}/build/reports/android-lint.html")
    }

    testOptions {
        suites {
            create("journeysTest") {
                targets {
                    create("default") {
                    }
                }
                useJunitEngine {
                    inputs += listOf(AgpTestSuiteInputParameters.TESTED_APKS)
                    includeEngines += listOf("journeys-test-engine")
                    enginesDependencies(libs.unitTest.junit.platformLauncher)
                    enginesDependencies(libs.junit.platform.engine)
                    enginesDependencies(libs.journeys.junit.engine)
                }
                targetVariants += listOf("debug")
            }
        }
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
    kover(projects.mobile.feature.coffees)
    kover(projects.mobile.feature.weather)
    kover(projects.mobile.feature.misc)
    kover(projects.mobile.data.coffees)
    kover(projects.mobile.data.weather)
    kover(projects.mobile.data.misc)
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
