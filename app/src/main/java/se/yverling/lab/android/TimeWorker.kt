package se.yverling.lab.android

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber
import java.util.*

internal class TimeWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Timber.tag("WorkerManager").d("Time is now ${Calendar.getInstance().time}")
        return Result.success()
    }
}
