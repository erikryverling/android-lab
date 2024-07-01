rootProject.name = "Android Lab"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":app",
    ":common:design-system",
    ":common:ui",
    ":data:coffees",
    ":data:misc",
    ":data:weather",
    ":feature:animation",
    ":feature:coffees",
    ":feature:misc",
    ":feature:weather",
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
