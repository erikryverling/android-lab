import java.util.*

plugins {
    id("androidlab.hilt-library-conventions")
    alias(libs.plugins.protobuf)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.bundles.retrofit)
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
