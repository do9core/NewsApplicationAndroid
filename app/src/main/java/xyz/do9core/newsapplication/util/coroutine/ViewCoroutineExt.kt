package xyz.do9core.newsapplication.util.coroutine

import android.view.View
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

fun View.autoDispose(job: Job) {
    val listener = ViewListener(this, job)
    this.addOnAttachStateChangeListener(listener)
}

val View.autoDisposeScope: CoroutineScope
    get() = SafeCoroutineScope(Dispatchers.Main)

private class ViewListener(
    private val view: View,
    private val job: Job
) : View.OnAttachStateChangeListener, CompletionHandler {

    override fun onViewAttachedToWindow(v: View?) = Unit

    override fun onViewDetachedFromWindow(v: View?) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }

    override fun invoke(cause: Throwable?) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }
}

private class ViewAutoDisposeInterceptorImpl(
    private val view: View
) : ContinuationInterceptor {

    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        continuation.context[Job]?.let {
            view.autoDispose(it)
        }
        return continuation
    }
}