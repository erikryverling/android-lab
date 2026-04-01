package se.yverling.lab.android.convention

import Versions
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


internal fun Project.commonAndroidConfig() {
    android {
        compileSdk = Versions.compileSdk

        when (this) {
            is ApplicationExtension -> {
                defaultConfig { minSdk = Versions.minSdk }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
                buildFeatures { buildConfig = true }
            }
            is LibraryExtension -> {
                defaultConfig { minSdk = Versions.minSdk }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }
                buildFeatures { buildConfig = true }
            }
        }
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
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
