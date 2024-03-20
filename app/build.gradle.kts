plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("app.cash.paparazzi")
    id("org.jetbrains.kotlinx.kover")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlinCompiler.get()
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

    testImplementation(libs.unitTest.junit4)
    testImplementation(project(":data:coffees"))

    androidTestImplementation(libs.androidTest.runner)
    androidTestImplementation(libs.androidTest.hilt)
    androidTestImplementation(libs.androidTest.work)
    androidTestImplementation(libs.unitTest.coroutines)
    androidTestImplementation(libs.unitTest.kotest.assertions)
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
