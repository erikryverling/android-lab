package se.yverling.lab.android.data.weather.model

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeDataStore<T>(initialValue: T) : DataStore<T> {
    private val state = MutableStateFlow(initialValue)
    override val data: Flow<T> = state

    override suspend fun updateData(transform: suspend (t: T) -> T): T {
        val updated = transform(state.value)
        state.value = updated
        return updated
    }
}
