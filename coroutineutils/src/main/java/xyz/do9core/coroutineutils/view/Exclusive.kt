package xyz.do9core.coroutineutils.view

import android.util.Log
import android.view.View
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

typealias OnTapListener = () -> Unit

private object ExclusiveEventHandler {

    private val exclusiveChannel by lazy { Channel<OnTapListener>() }
    private val scope = SupervisorScope()

    init {

        suspend fun eventLoop() {
            for (event in exclusiveChannel) {
               event.invoke()
                delay(1000)
            }
        }

        scope.launch { eventLoop() }
    }

    fun offerEvent(event: OnTapListener) {
        if (!exclusiveChannel.offer(event)) {
            Log.w("Exclusive", "Click to fast and been abort.")
        }
    }
}

fun View.exclusiveTap(onTap: OnTapListener) = setOnClickListener {
    ExclusiveEventHandler.offerEvent(onTap)
}