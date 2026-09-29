rootProject.name = "android-lab"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":mobile:app",
    ":mobile:common:design-system",
    ":mobile:common:model",
    ":mobile:common:ui",
    ":mobile:data:ai",
    ":mobile:data:coffees",
    ":mobile:data:misc",
    ":mobile:data:weather",
    ":mobile:data:weather:proto",
    ":mobile:feature:ai",
    ":mobile:feature:coffees",
    ":mobile:feature:misc",
    ":mobile:feature:weather",
    ":mobile:test:benchmark",
    ":mobile:test:utils",
    ":tv:app",
    ":tv:design-system",
    ":tv:ui",
    ":wear:app"
)

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    // Configures the JVM toolchain for Kotlin and Java compilation tasks.
    // If JDK 21 is not found locally, Gradle downloads and caches it automatically
    // using the Foojay Disco API configured by the foojay-resolver-convention plugin.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
