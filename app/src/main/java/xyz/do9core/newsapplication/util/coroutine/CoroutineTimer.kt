package xyz.do9core.newsapplication.util.coroutine

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import xyz.do9core.newsapplication.util.Event
import xyz.do9core.newsapplication.util.trigger
import java.time.Duration
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private typealias TimerCallback = () -> Unit

private inline val Job.isInactive get() = !this.isActive
private inline val Job.isFinished get() = this.isCancelled || this.isCompleted

class CoroutineTimer(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    private val durationInMs: Long,
    private val callback: TimerCallback
) {

    private val coroutineScope: CoroutineScope
        = CoroutineScope(Dispatchers.Default + coroutineContext)

    private var _timerJob = buildTimerJob()

    fun start() {
        if (_timerJob.isFinished) {
            _timerJob = buildTimerJob()
        }
        if (_timerJob.isInactive) {
            _timerJob.start()
        }
    }

    fun stop() {
        _timerJob.cancel()
    }

    private suspend fun timerLoop() {
        while (true) {
            withContext(Dispatchers.Main) { callback() }
            delay(durationInMs)
        }
    }

    private fun buildTimerJob(): Job {
        return coroutineScope.launch { timerLoop() }
    }
}

@Suppress("NOTHING_TO_INLINE")
@RequiresApi(Build.VERSION_CODES.O)
inline fun launchTimer(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    duration: Duration,
    noinline callback: TimerCallback
) = launchTimer(coroutineContext, duration.toMillis(), callback)

@Suppress("NOTHING_TO_INLINE")
@RequiresApi(Build.VERSION_CODES.O)
inline fun timerLiveData(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    duration: Duration
): LiveData<Event<Unit>> = timerLiveData(coroutineContext, duration.toMillis())

fun launchTimer(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    durationInMs: Long,
    callback: TimerCallback
) = CoroutineTimer(coroutineContext, durationInMs, callback)

fun timerLiveData(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    durationInMs: Long
): LiveData<Event<Unit>> = object : MutableLiveData<Event<Unit>>() {

    private val timer = launchTimer(coroutineContext, durationInMs) { this.trigger() }

    override fun onActive() { timer.start() }
    override fun onInactive() { timer.stop() }
}
