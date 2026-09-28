package se.yverling.lab.android.ai

import se.yverling.lab.android.common.model.Coffee

interface AiRepository {
    suspend fun prompt(): Result<Coffee>
}

class EmptyAiResponseException(message: String = "AI response was empty or blocked") : Exception(message)
