package se.yverling.lab.android.feature.misc

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import se.yverling.lab.android.design.theme.AndroidLabTheme
import se.yverling.lab.android.design.theme.DefaultSpace
import se.yverling.lab.android.design.theme.LargeSpace
import se.yverling.lab.android.design.theme.MediumSpace
import se.yverling.lab.android.design.theme.SmallSpace
import timber.log.Timber

internal const val REMOTE_IMAGE_URL = "https://erik.r.yverling.com/twinkle-logo.png"
private const val PAGE_COUNT = 7

/*
    Annotating a class as @Immutable means that you vouch that is will never change,
    and Compose will then mark it as 'stable' instead of 'unstable'
 */
@Immutable
data class Person(val names: MutableList<String>)

/*
    Annotating a class as @Stable means that you vouch that Compose will be notified,
    when it changes (usually through mutableState), even-though it's mutable.
    As with @Immutable this will make Compose mark it as 'stable' instead of 'unstable'.
 */
@Stable
data class Employer(var name: String)

const val MiscScreenDestination = "miscScreen"

@SuppressLint("AutoboxingStateCreation")
@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MiscScreen(
    modifier: Modifier = Modifier,
    onDeepLinkButtonClick: () -> Unit,
    viewModel: MiscViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var dynamicTheme by remember { mutableStateOf(false) }

    var evenCounter = 0

    AndroidLabTheme(dynamicColor = dynamicTheme) {
        val coroutineScope = rememberCoroutineScope()

        val modalSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
            skipHalfExpanded = false
        )

        var isBottomSheetFullScreen by remember { mutableStateOf(false) }

        val roundedCornerRadius = if (isBottomSheetFullScreen) 0.dp else DefaultSpace

        val bottomSheetModifier = if (isBottomSheetFullScreen) {
            Modifier.fillMaxSize()
        } else {
            Modifier.fillMaxWidth()
        }

        BackHandler(modalSheetState.isVisible) {
            coroutineScope.launch { modalSheetState.hide() }
        }

        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetShape = RoundedCornerShape(
                topStart = roundedCornerRadius,
                topEnd = roundedCornerRadius
            ),
            sheetContent = {
                Row(
                    modifier = bottomSheetModifier.padding(MediumSpace),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.padding(end = MediumSpace),
                        onClick = { isBottomSheetFullScreen = !isBottomSheetFullScreen }
                    ) {
                        val text =
                            if (isBottomSheetFullScreen) {
                                stringResource(
                                    R.string.bottom_sheet_button_toogle_half_expanded_button_title
                                )
                            } else {
                                stringResource(
                                    R.string.bottom_sheet_button_toogle_fullscreen_button_title
                                )
                            }
                        Text(text)
                    }

                    Button(
                        onClick = { coroutineScope.launch { modalSheetState.hide() } }
                    ) {
                        Text(stringResource(R.string.bottom_sheet_button_hide_button_title))
                    }
                }
            }
        ) {
            Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
                Column(
                    modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    /*
                        mutableState() will make sure that the composable is recomposed whenever the counter value is changed.
                        remember() will make sure that it's also saved during recomposition and not reset.
                        So in theory we could use only mutableState() if we move the counter outside of the composable
                     */
                    var counter by remember { mutableStateOf(0) }

                    /*
                        This will crate a state value that only updates when the counter changes from being larger than 0
                        and will be more efficient than checking counter > 0 directly
                     */
                    val showSnackbar by remember {
                        derivedStateOf {
                            counter > 0
                        }
                    }

                    /*
                        To call a suspended function from within a Composable we need to use a LaunchedEffect.
                        To control when the launched effect is called we provide a key. The launched effect will
                        then be run each time the containing composable is recomposed and the key has changed
                        value
                     */
                    if (showSnackbar) {
                        /*
                            This will guarantee to refer to the latest logNumberOfManualRecompositions() function that the closest
                            composable (Column) was recomposed with
                         */
                        val currentNumberOfManualRecompositions by rememberUpdatedState(
                            ::logNumberOfManualRecompositions
                        )

                        LaunchedEffect(counter) {
                            scope.launch {
                                currentNumberOfManualRecompositions(counter)
                                snackbarHostState.currentSnackbarData?.dismiss()

                                snackbarHostState.showSnackbar(
                                    message = "Number of manual recompositions: $counter",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }

                    MiscButton(
                        text = if (dynamicTheme) R.string.custom_theme else R.string.dynamic_theme,
                        modifier = Modifier.padding(bottom = LargeSpace, top = LargeSpace)
                    ) {
                        dynamicTheme = !dynamicTheme
                    }

                    MiscButton(
                        text = R.string.deep_link_button_title,
                        modifier = Modifier.padding(bottom = LargeSpace)
                    ) { onDeepLinkButtonClick.invoke() }

                    MiscButton(
                        text = R.string.recompose_button_title,
                        modifier = Modifier.padding(bottom = LargeSpace)
                    ) {
                        counter++
                        if (counter % 2 == 0) evenCounter++
                    }

                    OpenBottomSheetButton(
                        Modifier.padding(bottom = LargeSpace),
                        coroutineScope,
                        modalSheetState
                    )

                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    Text(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        text = "Collecting: ${state.name}",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )

                    SkippableComposable(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        person = Person(mutableListOf("Cloud", "Strife")),
                        employer = Employer("Shinra")
                    )

                    AsyncImage(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        model = REMOTE_IMAGE_URL,
                        placeholder = painterResource(R.drawable.baseline_image_24),
                        error = painterResource(R.drawable.baseline_broken_image_24),
                        contentDescription = stringResource(
                            R.string.remote_image_content_description
                        ),
                        contentScale = ContentScale.Fit
                    )

                    AutoFillTextField(
                        modifier = Modifier.padding(bottom = SmallSpace),
                        InputType.EMAIl
                    )

                    AutoFillTextField(
                        modifier = Modifier.padding(bottom = LargeSpace),
                        InputType.PASSWORD
                    )

                    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT })

                    HorizontalPager(
                        state = pagerState
                    ) { page ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = LargeSpace),
                            text = "Page ${page + 1}",
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Center
                        )
                    }

                    PageIndicator(
                        pagerState = pagerState,
                        modifier = Modifier.padding(bottom = LargeSpace)
                    )

                    // This would run on each recomposition.
                    // val list = createHugeList()

                    /* It's better to wrap it in a remember() and only update it when it makes sense.
                 In this case whenever evenCounter is changed.
                 Also note that the key to remember() could be of any type. */
                    val list by remember(evenCounter) { mutableStateOf(createHugeList()) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OpenBottomSheetButton(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    modalSheetState: ModalBottomSheetState
) {
    Button(
        modifier = modifier,
        onClick = {
            coroutineScope.launch {
                if (modalSheetState.isVisible) {
                    modalSheetState.hide()
                } else {
                    modalSheetState.show()
                }
            }
        }
    ) {
        Text(stringResource(R.string.bottom_sheet_button_title))
    }
}

@Composable
fun MiscButton(@StringRes text: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Text(
            stringResource(text),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun RecomposeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Text(
            stringResource(R.string.recompose_button_title),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

// This Composable should be skipped during recomposition
@Composable
fun SkippableComposable(modifier: Modifier = Modifier, person: Person, employer: Employer) {
    Text(
        modifier = modifier,
        text = "Name: ${person.names[0]} ${person.names[1]}, employer: ${employer.name}",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

/**
 * This does currently not work with 1Password, since it's not setting the correct
 * inputType. AutofillType.Password / KeyboardType.Password works as expected though.
 * Try this out every now and then do see if Google has added support for it in Compose.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoFillTextField(modifier: Modifier = Modifier, inputType: InputType) {
    var value by remember { mutableStateOf("") }

    val autofillNode = AutofillNode(
        autofillTypes =
        if (inputType == InputType.EMAIl) {
            listOf(AutofillType.EmailAddress)
        } else {
            listOf(AutofillType.Password)
        },
        onFill = { value = it }
    )
    val autofill = LocalAutofill.current

    LocalAutofillTree.current += autofillNode

    OutlinedTextField(
        modifier = modifier
            .onGloballyPositioned {
                autofillNode.boundingBox = it.boundsInWindow()
            }
            .onFocusChanged { focusState ->
                autofill?.run {
                    if (focusState.isFocused) {
                        requestAutofillForNode(autofillNode)
                    } else {
                        cancelAutofillForNode(autofillNode)
                    }
                }
            },
        value = value,
        label = { Text(text = if (inputType == InputType.EMAIl) "Email" else "Password") },
        keyboardOptions =
        if (inputType == InputType.EMAIl) {
            KeyboardOptions(keyboardType = KeyboardType.Email)
        } else {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        },
        visualTransformation =
        if (inputType == InputType.EMAIl) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = { value = it },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // TODO This should be set by default. Hmm...
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

enum class InputType {
    EMAIl,
    PASSWORD
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(PAGE_COUNT) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            Box(
                modifier = Modifier
                    .padding(SmallSpace)
                    .clip(CircleShape)
                    .background(color)
                    .size(MediumSpace)
            )
        }
    }
}

private fun createHugeList(): List<Int> {
    Timber.d("Creating huge list!")
    val mutableList = mutableListOf<Int>()
    for (i in 0..1000) {
        mutableList.add(i)
    }
    return mutableList.toList()
}

fun logNumberOfManualRecompositions(counter: Int) {
    Timber.d("Number of manual recompositions: $counter")
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun MiscButtonPreview() {
    AndroidLabTheme {
        MiscButton(text = R.string.deep_link_button_title) {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun RecomposeButtonPreview() {
    AndroidLabTheme {
        RecomposeButton {}
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun SkippableComposablePreview() {
    AndroidLabTheme {
        SkippableComposable(
            person = Person(mutableListOf("Cloud", "Strife")),
            employer = Employer("Shinra")
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun AutoFillTextFieldPreview() {
    AndroidLabTheme {
        AutoFillTextField(inputType = InputType.EMAIl)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PageIndicatorPreview() {
    AndroidLabTheme {
        PageIndicator(pagerState = rememberPagerState(pageCount = { PAGE_COUNT }))
    }
}
