package se.yverling.lab.android.ai

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import se.yverling.lab.android.data.ai.BuildConfig
import javax.inject.Inject
import kotlinx.serialization.json.Json
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.data.ai.R

internal class AiRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : AiRepository {
    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash-001",
        apiKey = BuildConfig.API_KEY,
        generationConfig = generationConfig {
            temperature = 1f
            topK = 32
            topP = 1f
            maxOutputTokens = 4096
            responseMimeType = "application/json"
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
        )
    )

    override fun promptFlow(): Flow<Coffee> = flow {
        val response = model.generateContent(context.getString(R.string.prompt))
        val coffee = Json.decodeFromString<Coffee>(response.text!!)
        emit(coffee)
    }
}
