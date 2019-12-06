package xyz.do9core.newsapplication.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import java.time.Duration

private typealias TimerCallback = () -> Unit

private inline val Job.isInactive get() = !this.isActive
private inline val Job.isFinished get() = this.isCancelled || this.isCompleted

class CoroutineTimer(
    private val scope: CoroutineScope,
    private val durationInMs: Long,
    private val callback: TimerCallback? = null
) : LifecycleObserver {

    private var _timerJob = buildTimerJob()

    fun start() {
        if(_timerJob.isFinished) {
            _timerJob = buildTimerJob()
        }
        if(_timerJob.isInactive) {
            _timerJob.start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        _timerJob.cancel()
    }

    private suspend fun timerLoop() {
        while(true) {
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
            delay(durationInMs)
        }
    }

    private fun buildTimerJob(): Job {
        return scope.launch(
            context = Dispatchers.Default,
            start = CoroutineStart.LAZY
        ) {
            timerLoop()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun CoroutineScope.launchTimer(duration: Duration, callback: TimerCallback? = null) =
    launchTimer(duration.toMillis(), callback)

fun CoroutineScope.launchTimer(durationInMs: Long, callback: TimerCallback? = null) =
    CoroutineTimer(this, durationInMs, callback)