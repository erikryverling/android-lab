package se.yverling.lab.android.convention

import Versions
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


internal fun Project.commonAndroidConfig() {
    android {
        compileSdk = Versions.compileSdk

        when (this) {
            is ApplicationExtension -> {
                defaultConfig { minSdk = Versions.minSdk }
                buildFeatures { buildConfig = true }
            }
            is LibraryExtension -> {
                defaultConfig { minSdk = Versions.minSdk }
                buildFeatures { buildConfig = true }
            }
        }
    }

    kotlin {
        jvmToolchain(Versions.jvm.toInt())
    }
}

internal fun Project.commonAndroidJunit5() {
    android {
        when (this) {
            is ApplicationExtension -> testOptions { unitTests.all { it.useJUnitPlatform() } }
            is LibraryExtension -> testOptions { unitTests.all { it.useJUnitPlatform() } }
        }
    }
}

internal fun Project.android(action: CommonExtension.() -> Unit) {
    extensions.configure(CommonExtension::class.java, action)
}

internal fun Project.kotlin(action: KotlinAndroidProjectExtension.() -> Unit) {
    extensions.configure(KotlinAndroidProjectExtension::class.java, action)
}
