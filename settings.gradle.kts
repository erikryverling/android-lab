rootProject.name = "android-lab"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":mobile:app",
    ":mobile:common:design-system",
    ":mobile:common:ui",
    ":mobile:common:model",
    ":mobile:data:coffees",
    ":mobile:data:misc",
    ":mobile:data:weather",
    ":mobile:data:weather:proto",
    ":mobile:data:ai",
    ":mobile:feature:coffees",
    ":mobile:feature:misc",
    ":mobile:feature:weather",
    ":mobile:feature:ai",
    ":mobile:test:benchmark",
    ":mobile:test:utils",
    ":wear:app",
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
