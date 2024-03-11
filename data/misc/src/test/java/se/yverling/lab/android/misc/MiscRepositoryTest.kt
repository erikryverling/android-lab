package se.yverling.lab.android.misc

import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class MiscRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Test
    fun `longRunningFlow() should run successfully`() = testScope.runTest {
        val repository = MiscRepository(testDispatcher)

        repository.longRunningFlow()
            .take(10)
            .collect {
                println(it)
                println("Collecting on ${Thread.currentThread().name}")
                it.shouldBeGreaterThanOrEqual(0)
            }
    }
}
