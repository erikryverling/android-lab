package se.yverling.lab.android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import se.yverling.lab.android.coffees.navigation.CoffeesRoute
import se.yverling.lab.android.coffees.navigation.coffeesGraph
import se.yverling.lab.android.coffees.ui.CoffeeDetailsScreenDeepLinkUri
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.feature.animation.AnimationScreen
import se.yverling.lab.android.feature.misc.MiscScreen
import se.yverling.lab.android.ui.BottomNavigation
import se.yverling.lab.android.ui.BottomNavigationItems
import se.yverling.lab.android.weather.ui.WeatherScreen
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* This is an example of a lifecycle-safe way of dealing with flows that will start the long running
           flow on STARTED, cancel it on PAUSE and relaunch it on STARTED (again) */
        lifecycleScope.launch {
            longRunningFlow()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {}
            /* NOTE! flowWithLifecycle() will suspend until the lifecycle is DESTROYED.
             This means that code after this call won't have time to execute. */
        }

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<TimeWorker>(1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)

        setContent {
            AndroidLabTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        val items = listOf(
                            BottomNavigationItems.Coffees,
                            BottomNavigationItems.Weather,
                            BottomNavigationItems.Animation,
                            BottomNavigationItems.Misc
                        )

                        BottomNavigation(navController = navController, items = items)
                    },
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = CoffeesRoute,
                        Modifier.padding(innerPadding)
                    ) {
                        coffeesGraph(navController)

                        composable(BottomNavigationItems.Weather.route) {
                            WeatherScreen()
                        }

                        composable(BottomNavigationItems.Animation.route) {
                            AnimationScreen()
                        }

                        val miscScreenAnimationDuration = 700

                        composable(
                            BottomNavigationItems.Misc.route,

                            // This is an example on how you could animate the navigation between Composables
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(miscScreenAnimationDuration)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(miscScreenAnimationDuration)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Up,
                                    animationSpec = tween(miscScreenAnimationDuration)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Down,
                                    animationSpec = tween(miscScreenAnimationDuration)
                                )
                            },
                        ) {
                            MiscScreen(onDeepLinkButtonClick = {
                                val firstCoffeeIndex = 0
                                navController.navigate(Uri.parse("$CoffeeDetailsScreenDeepLinkUri/$firstCoffeeIndex"))
                            })
                        }
                    }
                }
            }
        }
    }

    private suspend fun longRunningFlow(): Flow<Boolean> {
        val tag = "LongRunningFlowInMainActivity"
        return flow {
            Timber.tag(tag).d("New instance created")
            while (true) {
                delay(3000)
                Timber.tag(tag).d("Emitting event from MainActivity")
                emit(true)
            }
        }.flowOn(Dispatchers.Default)
    }
}
