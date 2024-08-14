import java.util.*

plugins {
    id("androidlab.hilt-library-conventions")
}

dependencies {
    implementation(project(":common:model"))

    implementation(libs.generativeai)
    implementation(libs.kotlinx.serialization)
}

android {
    namespace = "se.yverling.lab.android.data.ai"

    defaultConfig {
        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        val apiKey = properties.getProperty("geminiApiKey")

        buildConfigField("String", "API_KEY", apiKey)
    }

    buildFeatures {
        buildConfig = true
    }
}
