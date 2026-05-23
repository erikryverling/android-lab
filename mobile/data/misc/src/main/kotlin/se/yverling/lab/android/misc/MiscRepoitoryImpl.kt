package se.yverling.lab.android.misc

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import se.yverling.lab.android.data.misc.R
import se.yverling.lab.android.misc.model.CarouselItem
import timber.log.Timber
import javax.inject.Inject

class MiscRepositoryImpl(private val dispatcher: CoroutineDispatcher): MiscRepository {
    @Inject
    constructor() : this(dispatcher = Dispatchers.Default)

    /**
     * This is a just mock of a long running (backend) flow
     */
    override fun longRunningFlow(): Flow<Int> = flow {
        val tag = "LongRunningFlowInMiscRepository"
        Timber.tag(tag).d("New instance created")
        var counter = 0
        while (true) {
            delay(1000)
            Timber.tag(tag).d("Emitting event $counter from MiscRepository")
            emit(counter++)
        }
    }.flowOn(dispatcher)

    val carouselItems =
        listOf(
            CarouselItem(0, R.drawable.carousel_image_1, R.string.carousel_image_1_description),
            CarouselItem(1, R.drawable.carousel_image_2, R.string.carousel_image_2_description),
            CarouselItem(2, R.drawable.carousel_image_3, R.string.carousel_image_3_description),
            CarouselItem(3, R.drawable.carousel_image_4, R.string.carousel_image_4_description),
            CarouselItem(4, R.drawable.carousel_image_1, R.string.carousel_image_5_description),
            CarouselItem(5, R.drawable.carousel_image_2, R.string.carousel_image_6_description),
            CarouselItem(6, R.drawable.carousel_image_3, R.string.carousel_image_7_description),
            CarouselItem(7, R.drawable.carousel_image_4, R.string.carousel_image_8_description),
        )
}
