package se.yverling.lab.android.misc

import kotlinx.coroutines.flow.Flow

interface MiscRepository {
    fun longRunningFlow(): Flow<Int>
}
