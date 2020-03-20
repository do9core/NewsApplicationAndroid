package xyz.do9core.coroutineutils.view

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class SingleJobScope(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    errorHandler: CoroutineExceptionHandler? = null
) : CoroutineScope {

    override val coroutineContext: CoroutineContext

    init {
        val job = Job()
        coroutineContext = job + dispatcher + (errorHandler ?: EmptyCoroutineContext)
    }
}
