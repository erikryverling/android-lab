package se.yverling.lab.android.ai

import kotlinx.coroutines.flow.Flow
import se.yverling.lab.android.common.model.Coffee

interface AiRepository {
    fun promptFlow(): Flow<Coffee>
}

class EmptyAiResponseException(message: String = "AI response was empty or blocked") : Exception(message)
