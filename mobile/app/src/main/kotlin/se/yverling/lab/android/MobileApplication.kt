package se.yverling.lab.android

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class MobileApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }

        FirebaseApp.initializeApp(this)
        Firebase.appCheck.installAppCheckProviderFactory(
                // Now using google-services.json and AppCheck debug token instead of geminiApiKey property
            DebugAppCheckProviderFactory.getInstance(),
        )
    }
}
