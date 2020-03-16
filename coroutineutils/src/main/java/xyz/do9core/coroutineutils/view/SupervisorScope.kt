package xyz.do9core.coroutineutils.view

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal class SupervisorScope(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    errorHandler: CoroutineExceptionHandler? = null
) : CoroutineScope {

    override val coroutineContext: CoroutineContext

    init {
        val job = SupervisorJob()
        coroutineContext = job + dispatcher + (errorHandler ?: EmptyCoroutineContext)
    }
}