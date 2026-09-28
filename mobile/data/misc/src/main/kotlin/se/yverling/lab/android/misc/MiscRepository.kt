package se.yverling.lab.android.misc

import kotlinx.coroutines.flow.Flow
import se.yverling.lab.android.misc.model.CarouselItem

interface MiscRepository {
    fun longRunningFlow(): Flow<Int>
    val carouselItems: List<CarouselItem>
}
