package se.yverling.lab.android.feature.misc

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val DEFAULT_DURATION_IN_MILLIS = 400
private const val DEFAULT_DELAY_IN_MILLIS = 300

@Composable
fun AnimationContent(modifier: Modifier = Modifier) {
    var started by remember { mutableStateOf(false) }
    var logoShown by remember { mutableStateOf(false) }

    val logoSize by animateSizeAsState(
        trigger = started,
        start = 1500.dp,
        finish = 100.dp,
        onFinish = {
            logoShown = true
        }
    )

    val logoAlpha by animateAlpha(trigger = started)

    val androidLabWidth by animateSizeAsState(
        trigger = logoShown,
        start = 1500.dp,
        finish = 350.dp
    )

    val androidLabOffset by animateOffsetAsState(
        trigger = logoShown,
        start = 270.dp,
        finish = 90.dp
    )

    val androidLabTextStyle by animateTextStyleAsState(
        trigger = logoShown,
        start = MaterialTheme.typography.displayMedium.copy(fontSize = 200.sp),
        finish = MaterialTheme.typography.displayMedium
    )

    val androidLabAlpha by animateAlpha(trigger = logoShown)

    LaunchedEffect(Unit) {
        started = !started
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_lightbulb_24),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                MaterialTheme.colorScheme.primary
            ),
            alpha = logoAlpha,
            modifier = modifier.requiredSize(logoSize),
            contentDescription = stringResource(R.string.image_content_description)
        )

        Text(
            text = stringResource(R.string.android_lab),
            modifier
                .requiredWidth(androidLabWidth)
                .offset(y = androidLabOffset),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = androidLabAlpha),
            style = androidLabTextStyle
        )
    }
}

@Composable
fun animateSizeAsState(
    trigger: Boolean,
    start: Dp,
    finish: Dp,
    durationInMillis: Int = DEFAULT_DURATION_IN_MILLIS,
    delayInMillis: Int = DEFAULT_DELAY_IN_MILLIS,
    onFinish: () -> Unit = {}
): State<Dp> {
    return animateDpAsState(
        targetValue = if (trigger) finish else start,
        animationSpec = keyframes {
            this.durationMillis = durationInMillis
            this.delayMillis = delayInMillis
        },
        finishedListener = {
            onFinish.invoke()
        }, label = "size"
    )
}

@Composable
fun animateOffsetAsState(
    trigger: Boolean,
    start: Dp,
    finish: Dp = 0.dp,
    durationInMillis: Int = DEFAULT_DURATION_IN_MILLIS,
    delayInMillis: Int = DEFAULT_DELAY_IN_MILLIS,
    onFinish: (Dp) -> Unit = {}
): State<Dp> {
    return animateDpAsState(
        targetValue = if (trigger) finish else start,
        animationSpec = keyframes {
            this.durationMillis = durationInMillis
            this.delayMillis = delayInMillis
        },
        finishedListener = { currentValue ->
            onFinish(currentValue)
        }, label = "offsets"
    )
}

@Composable
fun animateTextStyleAsState(
    trigger: Boolean,
    start: TextStyle,
    finish: TextStyle,
    onFinish: (TextStyle) -> Unit = {}
): State<TextStyle> {
    val targetValue = if (trigger) finish else start

    val animation = remember { Animatable(0f) }
    var previousTextStyle by remember { mutableStateOf(targetValue) }
    var nextTextStyle by remember { mutableStateOf(targetValue) }

    val textStyleState = remember(animation.value) {
        derivedStateOf {
            lerp(previousTextStyle, nextTextStyle, animation.value)
        }
    }

    LaunchedEffect(targetValue) {
        previousTextStyle = textStyleState.value
        nextTextStyle = targetValue
        animation.snapTo(0f)
        animation.animateTo(
            1f,
            animationSpec = keyframes {
                this.durationMillis = DEFAULT_DURATION_IN_MILLIS
                this.delayMillis = DEFAULT_DELAY_IN_MILLIS
            }
        )
        onFinish.invoke(textStyleState.value)
    }

    return textStyleState
}

@Composable
fun animateAlpha(
    trigger: Boolean,
    durationInMillis: Int = DEFAULT_DURATION_IN_MILLIS,
    delayInMillis: Int = DEFAULT_DELAY_IN_MILLIS,
    onFinish: (Float) -> Unit = {}
): State<Float> {
    return animateFloatAsState(
        targetValue = if (trigger) 1f else 0f,
        animationSpec = keyframes {
            this.durationMillis = durationInMillis
            this.delayMillis = delayInMillis
        },
        finishedListener = { currentValue ->
            onFinish(currentValue)
        }, label = "alpha"
    )
}
