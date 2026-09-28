package se.yverling.lab.android.feature.misc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import se.yverling.lab.android.misc.MiscRepository
import se.yverling.lab.android.misc.model.CarouselItem

class FakeMiscRepository(
    override val carouselItems: List<CarouselItem> = emptyList()
) : MiscRepository {
    val flow = MutableSharedFlow<Int>()

    override fun longRunningFlow(): Flow<Int> = flow

    suspend fun emit(number: Int) {
        println("Emitting: $number")
        flow.emit(number)
    }
}
