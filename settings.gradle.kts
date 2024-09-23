rootProject.name = "Android Lab"
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
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
