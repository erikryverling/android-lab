rootProject.name = "android-lab"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":app",
    ":common:design-system",
    ":common:ui",
    ":common:model",
    ":data:coffees",
    ":data:misc",
    ":data:weather",
    ":data:ai",
    ":feature:coffees",
    ":feature:misc",
    ":feature:weather",
    ":feature:ai",
    ":test:benchmark",
    ":test:utils",
)

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
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
