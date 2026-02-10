---
name: upgrade-dependencies
description: Upgrades the project dependencies
---

## Finding Latest Versions

### For Gradle and Gradle Plugins
- Use https://gradle.org/releases/ for Gradle version updates
- Use https://plugins.gradle.org for Gradle plugin versions
- Check plugin-specific GitHub releases pages for the most accurate version information

### For Maven/Android Dependencies
1. **Primary Method**: Use WebSearch with specific queries:
   - Format: `"group:artifact" "version" latest maven 2026`
   - Example: `"androidx.navigation:navigation-compose" "2.9.7" OR "2.9.8" latest 2026`
   - Example: `"io.kotest:kotest-assertions-core" "6.0" OR "6.1" latest version`

2. **Verify Version Exists**: Always search for the exact version number to confirm it's available in Maven repositories

3. **Check Official Sources**:
   - AndroidX libraries: https://developer.android.com/jetpack/androidx/releases/
   - Kotlin libraries: https://github.com/Kotlin/[library-name]/releases
   - Firebase: https://firebase.google.com/support/release-notes/android
   - For each dependency group, search for their official release pages

4. **Version Compatibility**:
   - For Kotlin ecosystem libraries (KSP, kotlinx.serialization, etc.), check compatibility with the Kotlin version
   - Example: KSP version format is `kotlin-version-ksp-version` (e.g., `2.2.21-2.0.5`)
   - Some libraries like kotlinx-serialization have different versions for different Kotlin versions

5. **Special Cases**:
   - kotlin-dsl-plugin: Must match Gradle version requirements (check Gradle warnings)
   - Hilt: Check GitHub issues for Kotlin metadata compatibility
   - Coil 3.x: Uses different Maven coordinates (io.coil-kt.coil3 vs io.coil-kt)

## Upgrade Process

1. Analyze libs.versions.toml and gradle-wrapper.properties
2. For EACH dependency, search for the latest version using the methods above
3. Upgrade all dependencies in libs.versions.toml and gradle-wrapper.properties
4. Always prefer stable versions over alpha/beta when possible
5. Note any version constraints (e.g., Hilt not supporting Kotlin 2.3.x)
6. Add comments to the libs.versions.toml file if you decide not to upgrade to the latest version explaining why
7. Fix any compilation errors that arise from upgrades

## Verification Steps

Verify the changes by doing the following in order:
1. Run `./gradlew assembleDebug` to check compilation
2. Run `./gradlew test` to run unit tests (may have issues but compilation is key)
3. Start the Pixel_9_Pro_API_36 Android emulator with `emulator -avd Pixel_9_Pro_API_36 -no-snapshot-load &`
4. Wait for emulator to boot: `sleep 30 && adb devices`
5. Install the APK: `adb -s emulator-5556 install -r app/build/outputs/apk/debug/app-debug.apk`
6. Launch and verify app runs: `adb -s emulator-5556 shell am start -n se.yverling.lab.android/.MainActivity && sleep 5 && adb -s emulator-5556 shell "ps -A | grep se.yverling.lab.android"`
7. Stop emulator: `adb -s emulator-5556 emu kill`

## Final Steps

1. Summarize ALL changes in libs.versions.toml and gradle-wrapper.properties in a clear table format
2. Present to user for approval - do not proceed without approval
3. When approved, create a commit for the changes named "Bump dependencies"
4. Push the changes to remote
