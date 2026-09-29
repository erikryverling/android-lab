package se.yverling.lab.android.ai

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.generationConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.data.ai.R
import javax.inject.Inject

internal class AiRepositoryImpl @Inject constructor(@param:ApplicationContext private val context: Context) : AiRepository {
    private val model = Firebase
        .ai(
            backend = GenerativeBackend.googleAI(),
            useLimitedUseAppCheckTokens = true
        )
        .generativeModel(
            modelName = "gemini-3.5-flash-lite",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
            }
        )

    override suspend fun prompt(): Result<Coffee> = runCatching {
        val response = model.generateContent(context.getString(R.string.prompt))
        val text = response.text?.takeIf { it.isNotBlank() } ?: throw EmptyAiResponseException()
        Json.decodeFromString<Coffee>(text)
    }
}
