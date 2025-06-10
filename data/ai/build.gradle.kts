import java.util.Properties

plugins {
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.google.services)
}

dependencies {
    implementation(projects.common.model)

    implementation(libs.kotlinx.serialization)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.ai)
}

android {
    namespace = "se.yverling.lab.android.data.ai"

    defaultConfig {
        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        val apiKey = properties.getProperty("geminiApiKey")

        buildConfigField("String", "API_KEY", apiKey)
    }
}
