import java.util.Properties

plugins {
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.data.weather.proto)

    implementation(libs.bundles.ktor)

    implementation(libs.datastore)
    implementation(libs.protobuf)

    implementation(libs.kotlinx.datetime)
}


android {
    namespace = "se.yverling.lab.android.data.weather"

    defaultConfig {
        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        val apiKey = properties.getProperty("openWeatherMapApiKey")

        buildConfigField("String", "API_KEY", apiKey)
    }
}
