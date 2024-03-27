package se.yverling.lab.android.coffees.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import se.yverling.lab.android.coffees.ui.CoffeeDetailsScreen
import se.yverling.lab.android.coffees.ui.CoffeeDetailsScreenDeepLinkUri
import se.yverling.lab.android.coffees.ui.CoffeeDetailsScreenDestination
import se.yverling.lab.android.coffees.ui.CoffeeDetailsScreenNavArgument
import se.yverling.lab.android.coffees.ui.CoffeeListScreen
import se.yverling.lab.android.coffees.ui.CoffeeListScreenDestination
import timber.log.Timber

const val CoffeesRoute = "coffees"

fun NavGraphBuilder.coffeesGraph(navController: NavController) {
    navigation(startDestination = CoffeeListScreenDestination, route = CoffeesRoute) {
        composable(CoffeeListScreenDestination) {
            CoffeeListScreen(
                onCoffeeClicked = { coffeeIndex ->
                    // We could also call it with ?firstOptional=test or ?firstOptional=test&secondOptional=test
                    navController.navigate("$CoffeeDetailsScreenDestination/$coffeeIndex?sectionOptional=test")
                },
            )
        }

        composable(
            // Note! The optional argument are just to show how they work and serves no purpose in the app
            route = "$CoffeeDetailsScreenDestination/{$CoffeeDetailsScreenNavArgument}?firstOptional={firstOptional}&sectionOptional={sectionOptional}",
            arguments = listOf(
                navArgument(CoffeeDetailsScreenNavArgument) {
                    type = NavType.IntType
                },
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
                        "$CoffeeDetailsScreenDeepLinkUri/{$CoffeeDetailsScreenNavArgument}?firstOptional={firstOptional}&sectionOptional={sectionOptional}"
                },
            ),
        ) { backStackEntry ->
            CoffeeDetailsScreen(
                backStackEntry.arguments?.getInt(CoffeeDetailsScreenNavArgument)!!,
                onNavigateUp = {
                    navController.navigateUp()
                },
            )

            val firstOptional = backStackEntry.arguments?.getString("firstOptional")
            val secondOptional = backStackEntry.arguments?.getString("sectionOptional")
            Timber.d("First optional nav arg: $firstOptional, second optional nav arg: $secondOptional")
        }
    }
}
