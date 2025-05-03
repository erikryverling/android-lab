plugins {
    alias(libs.plugins.kotlin.dsl)
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.android.tools.gradle)
    compileOnly(libs.kotlin.gradle)

    // Expose the generated version catalog API to the plugins.
    implementation(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}

kotlin {
    sourceSets["main"].kotlin.srcDir("../../../buildSrc/src/main/kotlin")
}

gradlePlugin {
    plugins {
        plugins {
            create("AndroidLibraryConventionPlugin") {
                id = "se.yverling.lab.android.convention.android.library"
                implementationClass = "se.yverling.lab.android.convention.plugins.AndroidLibraryConventionPlugin"
            }

            create("ComposeConventionPlugin") {
                id = "se.yverling.lab.android.convention.compose"
                implementationClass = "se.yverling.lab.android.convention.plugins.ComposeConventionPlugin"
            }

            create("HiltConventionPlugin") {
                id = "se.yverling.lab.android.convention.hilt"
                implementationClass = "se.yverling.lab.android.convention.plugins.HiltConventionPlugin"
            }

            create("ApplicationConventionPlugin") {
                id = "se.yverling.lab.android.convention.application"
                implementationClass = "se.yverling.lab.android.convention.plugins.ApplicationConventionPlugin"
            }
        }
    }
}
