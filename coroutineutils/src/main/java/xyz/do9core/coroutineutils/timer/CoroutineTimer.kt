package xyz.do9core.coroutineutils.timer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.Duration
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

typealias TimerCallback = () -> Unit

interface CoroutineTimer {

    fun start()
    fun stop()
}

@Suppress("NOTHING_TO_INLINE")
@RequiresApi(Build.VERSION_CODES.O)
inline fun launchTimer(
    coroutineContext: CoroutineContext,
    duration: Duration,
    noinline callback: TimerCallback
) = launchTimer(coroutineContext, duration.toMillis(), callback)

fun launchTimer(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    durationInMs: Long,
    callback: TimerCallback
): CoroutineTimer = CoroutineTimerImpl(coroutineContext, durationInMs, callback)

@Suppress("NOTHING_TO_INLINE")
@RequiresApi(Build.VERSION_CODES.O)
inline fun timerLiveData(
    coroutineContext: CoroutineContext,
    duration: Duration
) = timerLiveData(coroutineContext, duration.toMillis())

fun timerLiveData(
    coroutineContext: CoroutineContext,
    durationInMs: Long
): LiveData<Unit> = object : MutableLiveData<Unit>() {

    private val timer = launchTimer(coroutineContext, durationInMs) { this.value = Unit }

    override fun onActive() = timer.start()
    override fun onInactive() = timer.stop()
}