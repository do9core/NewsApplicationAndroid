package xyz.do9core.coroutineutils.core

import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class SupervisorCoroutineScope(context: CoroutineContext) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext

    init {
        val supervisorJob = SupervisorJob(context[Job])
        coroutineContext = context + supervisorJob
    }

    override fun close() {
        coroutineContext.cancel()
    }
}

val mainSupervisorScope by lazy { SupervisorCoroutineScope(Dispatchers.Main) }