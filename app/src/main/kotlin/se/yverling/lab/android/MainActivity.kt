package se.yverling.lab.android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
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
import se.yverling.lab.android.coffees.ui.AdaptiveCoffeesScreen
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.feature.misc.MiscScreen
import se.yverling.lab.android.ui.BottomNavigation
import se.yverling.lab.android.ui.NavigationItem
import se.yverling.lab.android.ui.SideNavigation
import se.yverling.lab.android.weather.ui.WeatherScreen
import se.yverling.lab.android.weather.ui.WeatherScreenDeepLinkUri
import se.yverling.lab.android.weather.ui.WeatherScreenDestination
import timber.log.Timber
import java.util.concurrent.TimeUnit

@ExperimentalMaterial3Api
@ExperimentalMaterial3AdaptiveApi
@ExperimentalMaterialApi
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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

                val items = listOf(
                    NavigationItem.Coffees,
                    NavigationItem.Weather,
                    NavigationItem.Ai,
                    NavigationItem.Misc
                )

                val windowSizeClass = calculateWindowSizeClass(this)
                val isTabletLandscape = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
                        && windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium

                Scaffold(
                    bottomBar = {
                        if (!isTabletLandscape) {
                            BottomNavigation(navController = navController, items = items)
                        }
                    },
                ) { padding ->
                    Row {
                        if (isTabletLandscape) {
                            SideNavigation(navController = navController, items = items)
                        }

                        NavHost(
                            navController,
                            startDestination = NavigationItem.Coffees.route,
                            Modifier
                                .padding(padding)
                                .consumeWindowInsets(padding)
                        ) {
                            composable(NavigationItem.Coffees.route) {
                                AdaptiveCoffeesScreen()
                            }

                            weatherGraph()

                            composable(NavigationItem.Ai.route) {
                                AiScreen()
                            }

                            val miscScreenAnimationDuration = 700

                            composable(
                                NavigationItem.Misc.route,

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
                                MiscScreen(
                                    onDeepLinkButtonClick = {
                                        navController.navigate(Uri.parse("$WeatherScreenDeepLinkUri?sectionOptional=test"))
                                    }
                                )
                            }
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

// This is not really needed since a graph should contain more than one destination, but is here for demo purposes
@ExperimentalMaterialApi
fun NavGraphBuilder.weatherGraph() {
    navigation(startDestination = WeatherScreenDestination, route = NavigationItem.Weather.route) {
        composable(
            route = WeatherScreenDestination,
            arguments = listOf(
                navArgument("firstOptional") {
                    type = NavType.StringType
                    nullable = true
                },
                /*  Note that we're not declaring sectionOptional here, since it's not required but used to do
                    TYPE SAFE navigation. It is however RECOMMENDED to declare all arguments here.
                */
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "$WeatherScreenDeepLinkUri?firstOptional={firstOptional}&sectionOptional={sectionOptional}"
                },
            ),
        ) { backStackEntry ->
            WeatherScreen()

            val firstOptional = backStackEntry.arguments?.getString("firstOptional")
            val secondOptional = backStackEntry.arguments?.getString("sectionOptional")
            Timber.d("First optional nav arg: $firstOptional, second optional nav arg: $secondOptional")
        }

    }
}
