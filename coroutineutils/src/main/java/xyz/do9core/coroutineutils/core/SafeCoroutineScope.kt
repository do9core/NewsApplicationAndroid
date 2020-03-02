package xyz.do9core.coroutineutils.core

import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class SafeCoroutineScope(context: CoroutineContext) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext

    init {
        val supervisorJob = SupervisorJob(context[Job])
        coroutineContext = context + supervisorJob
    }

    override fun close() {
        coroutineContext.cancel()
    }
}