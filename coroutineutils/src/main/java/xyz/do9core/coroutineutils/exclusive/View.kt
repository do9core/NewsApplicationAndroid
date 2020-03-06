package xyz.do9core.coroutineutils.exclusive

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.do9core.coroutineutils.core.mainSupervisorScope
import java.time.Duration

const val DEFAULT_EXCLUSIVE_DURATION = 1000L

fun View.onExclusiveClick(
    durationInMs: Long = DEFAULT_EXCLUSIVE_DURATION,
    onClick: () -> Unit
) {
    val channel = Channel<Unit>()
    mainSupervisorScope.launch {
        for (event in channel) {
            onClick.invoke()
            delay(durationInMs)
        }
    }
    setOnClickListener { channel.offer(Unit) }
}

@Suppress("NOTHING_TO_INLINE")
@RequiresApi(Build.VERSION_CODES.O)
inline fun View.onExclusiveClick(
    duration: Duration,
    noinline onClick: () -> Unit
) = onExclusiveClick(duration.toMillis(), onClick)