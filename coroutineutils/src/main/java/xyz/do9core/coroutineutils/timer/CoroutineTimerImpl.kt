package xyz.do9core.coroutineutils.timer

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal class CoroutineTimerImpl(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    private val durationInMs: Long,
    private val callback: TimerCallback
) : CoroutineTimer {

    private val coroutineScope =
        CoroutineScope(Job() + Dispatchers.Default + coroutineContext)

    private var timerJob: Job? = null

    override fun start() {
        timerJob?.cancel("New job is starting.")
        timerJob = makeTimerJob()
    }

    override fun stop() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun makeTimerJob(): Job = coroutineScope.launch {
        while (true) {
            withContext(Dispatchers.Main) { callback() }
            delay(durationInMs)
        }
    }
}