plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.paparazzi)
    id("org.jetbrains.kotlinx.kover")
    id("org.jetbrains.kotlin.plugin.compose")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":common:design-system"))
    implementation(project(":feature:coffees"))
    implementation(project(":feature:weather"))
    implementation(project(":feature:animation"))
    implementation(project(":feature:misc"))

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
    testImplementation(project(":data:coffees"))

    androidTestImplementation(libs.androidTest.runner)
    androidTestImplementation(libs.androidTest.hilt)
    androidTestImplementation(libs.androidTest.work)
    androidTestImplementation(libs.unitTest.coroutines)
    androidTestImplementation(libs.unitTest.kotest.assertions)
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
    }

    compileOptions {
        // KSP only supports Java 17
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }

    namespace = "se.yverling.lab.android"

    defaultConfig {
        applicationId = "se.yverling.lab.android"
        versionCode = 10000 // Version & release number
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
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
        xmlReport = false
        htmlReport = true
        htmlOutput = file("${project.rootDir}/build/reports/android-lint.html")
    }
}

plugins.withId("app.cash.paparazzi") {
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
                because("LayoutLib and sdk-common depend on Guava's -jre published variant." +
                        "See https://github.com/cashapp/paparazzi/issues/906.")
            }
        }
    }
}

// Use for creating an aggregated Kover report
dependencies {
    kover(project(":feature:coffees"))
    kover(project(":feature:weather"))
    kover(project(":feature:misc"))
    kover(project(":data:coffees"))
    kover(project(":data:weather"))
    kover(project(":data:misc"))
}

koverReport {
    filters {
        includes {
            classes("*ViewModel*", "*UseCase*", "*Repository*")
        }

        excludes {
            classes("hilt_*", "*_Factory*", "*_Hilt*")
        }
    }
}
