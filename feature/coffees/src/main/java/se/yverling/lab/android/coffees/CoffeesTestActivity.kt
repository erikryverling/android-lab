package se.yverling.lab.android.coffees

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import se.yverling.lab.android.coffees.ui.CoffeeListScreen
import se.yverling.lab.android.design.theme.AndroidLabTheme

@AndroidEntryPoint
class CoffeesTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLabTheme {
                CoffeeListScreen {}
            }
        }
    }
}
