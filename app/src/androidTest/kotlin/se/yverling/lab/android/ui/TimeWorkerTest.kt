package se.yverling.lab.android.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import se.yverling.lab.android.TimeWorker
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class TimeWorkerTest {
    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun should_run_successfully() {
        val worker = TestListenableWorkerBuilder<TimeWorker>(context = context).build()
        runTest {
            val result = worker.doWork()
            result.shouldBeTypeOf<ListenableWorker.Result.Success>()
        }
    }
}
