package se.yverling.lab.android

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import se.yverling.lab.android.ai.AiRepository
import se.yverling.lab.android.common.model.Coffee

class FakeAiRepository(
    private var flow: Flow<Coffee> = flowOf()
) : AiRepository {
    fun emitCoffee(coffee: Coffee) {
        flow = flowOf(coffee)
    }

    fun emitError(throwable: Throwable) {
        flow = flow { throw throwable }
    }

    override fun promptFlow(): Flow<Coffee> = flow
}
