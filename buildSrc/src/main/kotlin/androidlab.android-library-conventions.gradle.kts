plugins {
    // Ideally we should use alias(), but that's currently hard in conventions plugins
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlinx.kover")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libsx.timber)

    testImplementation(libsx.bundleUnitTest)
    testImplementation(project(":test:utils"))
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

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    packaging {
        resources {
            excludes += listOf("META-INF/*")
        }
    }
}
