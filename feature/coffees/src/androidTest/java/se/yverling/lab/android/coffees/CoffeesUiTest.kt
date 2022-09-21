package se.yverling.lab.android.coffees

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CoffeesUiTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(CoffeesTestActivity::class.java)

    @Test
    fun should_tap_on_FAB_button_successfully() {
        val coffeeName = "Odo Carbonic"

        composeTestRule.onAllNodesWithText(coffeeName)[0].assertExists()
        composeTestRule.onAllNodesWithText(coffeeName.uppercase())[0].assertDoesNotExist()

        composeTestRule.onNodeWithTag("FAB").performClick()

        composeTestRule.onAllNodesWithText(coffeeName)[0].assertDoesNotExist()
        composeTestRule.onAllNodesWithText(coffeeName.uppercase())[0].assertExists()
    }
}
