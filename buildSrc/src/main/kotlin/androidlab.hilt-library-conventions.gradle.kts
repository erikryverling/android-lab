plugins {
    id("androidlab.android-library-conventions")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

dependencies {
    ksp(libsx.hiltAndroidCompiler)
    implementation(libsx.hiltAndroid)
}
