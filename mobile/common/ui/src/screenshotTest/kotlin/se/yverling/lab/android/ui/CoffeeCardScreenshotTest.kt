package se.yverling.lab.android.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import com.android.tools.screenshot.PreviewTest
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.design.theme.AndroidLabThemeWrapper

class CoffeeCardScreenshotTest {

    @PreviewTest
    @Preview(name = "Light Mode", showBackground = true)
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
    )
    @Preview(
        name = "Font Scale 150%",
        fontScale = 1.5f,
        showBackground = true,
    )
    @PreviewWrapper(AndroidLabThemeWrapper::class)
    @Composable
    fun CoffeeCardPreview() {
        CoffeeCard(
            coffee = Coffee(
                id = 0,
                name = "Odo Carbonic",
                roaster = "Gringo Nordic",
                origin = "Ethiopia",
                region = "Guji",
            ),
        )
    }
}
