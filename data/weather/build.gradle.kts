import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf")
    kotlin("plugin.serialization")
}

apply(from = "${rootProject.projectDir}/build.module.android.gradle")

android {
    namespace = "se.yverling.lab.android.data.weather"

    defaultConfig {

        val apiKey: String = gradleLocalProperties(rootDir).getProperty("openWeatherMapApiKey")
        buildConfigField("String", "API_KEY", apiKey)

        // Set to either Ktor or Retrofit
        buildConfigField("String", "API_CLIENT", "\"Ktor\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

protobuf {
    protoc {
        artifact = libs.versions.protobufCompilerArtifact.get()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation(libs.bundles.retrofit)

    implementation(libs.datastore)
    implementation(libs.protobuf)

    implementation(libs.kotlinx.datetime)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    testImplementation(libs.bundles.unitTest)
}
