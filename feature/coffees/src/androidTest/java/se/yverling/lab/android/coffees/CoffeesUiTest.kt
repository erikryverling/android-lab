package se.yverling.lab.android.coffees

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import org.junit.Rule
import org.junit.Test
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.coffees.ui.CoffeeList
import se.yverling.lab.android.design.theme.AndroidLabTheme

class CoffeesUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_tap_on_FAB_button_successfully() {
        composeTestRule.setContent {
            AndroidLabTheme {
                val coffees = listOf(
                    Coffee(
                        id = 0,
                        name = "name",
                        roaster = "roaster",
                        origin = "origin",
                        region = "region"
                    )
                )

                CoffeeList(
                    coffees = coffees,
                    isUppercase = false,
                    showHeading = false
                ) {

                }
            }
        }

        val coffeeName = "name"

        composeTestRule.onAllNodesWithText(coffeeName)[0].assertExists()
    }
}
