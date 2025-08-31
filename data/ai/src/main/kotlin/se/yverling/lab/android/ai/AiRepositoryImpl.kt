package se.yverling.lab.android.ai

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.generationConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import se.yverling.lab.android.common.model.Coffee
import se.yverling.lab.android.data.ai.R
import javax.inject.Inject

internal class AiRepositoryImpl @Inject constructor(@param:ApplicationContext private val context: Context) : AiRepository {
    private val model = Firebase
        .ai(backend = GenerativeBackend.googleAI())
        .generativeModel(
            modelName = "gemini-2.0-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
            }
        )

    override fun promptFlow(): Flow<Coffee> = flow {
        val response = model.generateContent(context.getString(R.string.prompt))
        val coffee = Json.decodeFromString<Coffee>(response.text!!)
        emit(coffee)
    }
}
