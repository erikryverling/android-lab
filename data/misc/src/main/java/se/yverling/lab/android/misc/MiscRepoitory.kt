package se.yverling.lab.android.misc

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class MiscRepository(private val dispatcher: CoroutineDispatcher) {
    @Inject
    constructor() : this(dispatcher = Dispatchers.Default)

    /**
     * This is a just mock of a long running (backend) flow
     */
    fun longRunningFlow(): Flow<Int> = flow {
        val tag = "LongRunningFlowInMiscRepository"
        Timber.tag(tag).d("New instance created")
        var counter = 0
        while (true) {
            delay(1000)
            Timber.tag(tag).d("Emitting event $counter from MiscRepository")
            emit(counter++)
        }
    }.flowOn(dispatcher)
}
