package se.yverling.lab.android.wear.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.layout.ScreenScaffold
import dagger.hilt.android.AndroidEntryPoint
import se.yverling.lab.android.wear.app.R
import se.yverling.lab.android.wear.app.ui.theme.WearAppTheme

@AndroidEntryPoint
@OptIn(ExperimentalHorologistApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearAppTheme {
                AppScaffold {
                    ScreenScaffold {
                        HelloWorldScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HelloWorldScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.hello_world),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalHorologistApi::class)
@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true,
)
@Composable
fun HelloWorldScreenPreview() {
    WearAppTheme {
        AppScaffold {
            ScreenScaffold {
                HelloWorldScreen()
            }
        }
    }
}
