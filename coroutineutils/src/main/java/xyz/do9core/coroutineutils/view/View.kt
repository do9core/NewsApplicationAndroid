package xyz.do9core.coroutineutils.view

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Duration

const val DEFAULT_EXCLUSIVE_DURATION = 1000L

fun View.onExclusiveClick(
    durationInMs: Long = DEFAULT_EXCLUSIVE_DURATION,
    onClick: () -> Unit
) {
    val channel = Channel<Unit>()
    val scope = MainScope() + CoroutineExceptionHandler { _, _ ->
        setOnClickListener(null)
        channel.close()
    }
    scope.launch {
        for (event in channel) {
            onClick.invoke()
            delay(durationInMs)
        }
    }
    setOnClickListener { channel.offer(Unit) }
}

@RequiresApi(Build.VERSION_CODES.O)
fun View.onExclusiveClick(
    duration: Duration,
    onClick: () -> Unit
) = onExclusiveClick(duration.toMillis(), onClick)

fun View.clicks(): Flow<Unit> = flow {
    val channel = Channel<Unit>()
    withContext(Dispatchers.Main.immediate) {
        setOnClickListener { channel.offer(Unit) }
    }
    try {
        for (event in channel) {
            emit(event)
        }
    } finally {
        GlobalScope.launch(Dispatchers.Main.immediate) {
            setOnClickListener(null)
        }
    }
}
