package xyz.do9core.newsapplication.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

data class Event<out T : Any>(val param: T) {

    var handled = false
        private set

    fun getParameter(): T? {
        if (!handled) {
            handled = true
            return param
        }
        return null
    }
}

fun <T : Any> LiveData<Event<T>>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    handler: (T) -> Unit
) =
    this.observe(lifecycleOwner, Observer {
        val event = it ?: return@Observer
        val data = event.getParameter() ?: return@Observer
        handler(data)
    })

fun <T : Any> LifecycleOwner.observeEvent(eventLiveData: LiveData<Event<T>>, handler: (T) -> Unit) =
    eventLiveData.observeEvent(this, handler)

@MainThread
fun MutableLiveData<Event<Unit>>.trigger() {
    this.value = Event(Unit)
}

@MainThread
fun <T : Any> MutableLiveData<Event<T>>.event(value: T) {
    this.value = Event(value)
}