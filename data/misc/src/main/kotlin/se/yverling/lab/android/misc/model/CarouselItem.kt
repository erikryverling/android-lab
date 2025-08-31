package se.yverling.lab.android.misc.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CarouselItem(
    val id: Int,
    @param:DrawableRes val imageResId: Int,
    @param:StringRes val contentDescriptionResId: Int
)
