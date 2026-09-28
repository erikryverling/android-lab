package se.yverling.lab.android

import se.yverling.lab.android.ai.AiRepository
import se.yverling.lab.android.common.model.Coffee

class FakeAiRepository(
    private var result: Result<Coffee> = Result.failure(IllegalStateException("Not initialized"))
) : AiRepository {
    fun emitCoffee(coffee: Coffee) {
        result = Result.success(coffee)
    }

    fun emitError(throwable: Throwable) {
        result = Result.failure(throwable)
    }

    override suspend fun prompt(): Result<Coffee> = result
}
