package xyz.do9core.newsapplication.util.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

class SafeCoroutineScope (
    context: CoroutineContext,
    errorHandler: CoroutineErrorListener? = null
) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        coroutineContext.cancelChildren()
    }
}