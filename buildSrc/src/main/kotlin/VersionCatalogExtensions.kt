
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

// This file is needed due to https://github.com/gradle/gradle/issues/15383


// [Plugin]
internal val VersionCatalog.pluginsKover: Provider<PluginDependency>?
    get() = findPluginOrThrow("kover")

// [Library]
internal val VersionCatalog.composeBom: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("compose.bom")

internal val VersionCatalog.hiltAndroidCompiler: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt.android.compiler")

internal val VersionCatalog.hiltAndroid: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("hilt.android")

internal val VersionCatalog.timber: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("timber")

internal val VersionCatalog.junitPlatform: Provider<MinimalExternalModuleDependency>
    get() = findLibraryOrThrow("unitTest-junit-platformLauncher")

// [Bundle]
internal val VersionCatalog.bundleCompose: Provider<ExternalModuleDependencyBundle>
    get() = findBundleOrThrow("compose")

internal val VersionCatalog.bundleUnitTest: Provider<ExternalModuleDependencyBundle>
    get() = findBundleOrThrow("unitTest")


// -- Helpers --

internal val Project.libsx: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

private fun VersionCatalog.findVersionOrThrow(name: String) =
    findVersion(name)
        .orElseThrow { NoSuchElementException("Version $name not found in version catalog") }
        .requiredVersion

private fun VersionCatalog.findPluginOrThrow(name: String) =
    findPlugin(name)
        .orElseThrow { NoSuchElementException("Plugin $name not found in version catalog") }

private fun VersionCatalog.findLibraryOrThrow(name: String) =
    findLibrary(name)
        .orElseThrow { NoSuchElementException("Library $name not found in version catalog") }

private fun VersionCatalog.findBundleOrThrow(name: String) =
    findBundle(name)
        .orElseThrow { NoSuchElementException("Bundle $name not found in version catalog") }
