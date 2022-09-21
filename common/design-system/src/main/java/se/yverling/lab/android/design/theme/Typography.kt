package se.yverling.lab.android.design.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import se.yverling.lab.android.common.design.R

private val videloka = FontFamily(
    Font(R.font.videloka_regular)
)

internal val Typography = Typography(
    displayMedium = TextStyle(fontFamily = videloka, fontSize = 45.sp),
    headlineMedium = TextStyle(fontFamily = videloka)
)
