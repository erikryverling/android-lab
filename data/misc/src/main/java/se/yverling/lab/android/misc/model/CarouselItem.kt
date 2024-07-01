package se.yverling.lab.android.misc.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    @StringRes val contentDescriptionResId: Int
)
