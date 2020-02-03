package xyz.do9core.newsapplication.util.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class UncaughtCoroutineExceptionHandler(
    val errorHandler: CoroutineErrorListener? = null
)  : CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
        errorHandler?.onError(exception)
    }
}