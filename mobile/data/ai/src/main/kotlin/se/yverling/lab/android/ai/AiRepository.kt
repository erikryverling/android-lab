package se.yverling.lab.android.ai

import kotlinx.coroutines.flow.Flow
import se.yverling.lab.android.common.model.Coffee

interface AiRepository {
    fun promptFlow(): Flow<Coffee>
}
