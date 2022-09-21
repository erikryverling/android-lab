rootProject.name = "Android Lab"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":app",
    ":common:design-system",
    ":common:ui",
    ":data:coffees",
    ":data:weather",
    ":data:misc",
    ":feature:coffees",
    ":feature:weather",
    ":feature:animation",
    ":feature:misc",
    ":test:benchmark",
    ":test:utils"
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
