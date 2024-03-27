package se.lab.android.test.snapshot

import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test
import se.yverling.lab.android.coffees.model.Coffee
import se.yverling.lab.android.coffees.ui.CoffeeList

class CoffeeListSnapshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        theme = "android:Theme.Material.Light.NoActionBar",
    )

    @Test
    fun launchComposable() {
        paparazzi.snapshot {
            CoffeeList(
                coffees = listOf(Coffee(
                    id = 0,
                    name = "name",
                    roaster = "roaster",
                    origin = "origin",
                    region = "region"
                )),
                showHeading = true,
                isUppercase = false,
                onCoffeeClicked = {}
            )
        }
    }
}
