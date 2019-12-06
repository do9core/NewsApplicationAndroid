package xyz.do9core.newsapplication.util

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.lifecycle.map

data class Event<out T>(val param: T) {

    private var handled = false

    fun handleEvent(handler: (T) -> Unit) {
        if(!handled) {
            handled = true
            handler.invoke(param)
        }
    }
}

class LiveEvent<T>() : LiveData<Event<T>>() {

    constructor(initialValue: T?): this() {
        if(initialValue == null) {
            super.setValue(null)
            return
        }
        super.setValue(Event(initialValue))
    }

    @MainThread
    fun event(param: T) = super.setValue(Event(param))

    @MainThread
    fun event(paramProducer: () -> T) = event(paramProducer())

    @AnyThread
    fun postEvent(param: T) = super.postValue(Event(param))

    @AnyThread
    fun postEvent(paramProducer: () -> T) = postEvent(paramProducer())
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveEvent<T>, handler: (T) -> Unit) =
    liveData.observe(this, EventObserver(handler))

fun LifecycleOwner.observeEvent(liveTrigger: LiveTrigger, handler: () -> Unit) =
    liveTrigger.observe(this, Observer { it.handleEvent { handler() } })

class LiveTrigger : LiveData<Event<Unit>>() {

    @MainThread
    fun signal() = super.setValue(Event(Unit))

    @AnyThread
    fun postSignal() = super.postValue(Event(Unit))
}

inline fun <T> LiveTrigger.convert(crossinline block: () -> T) =
    map { block() }

inline fun <T> LiveTrigger.convertSource(crossinline block: () -> LiveData<T>) =
    switchMap { block() }

class EventObserver<T>(
    private val eventHandler: (T) -> Unit
) : Observer<Event<T>> {

    override fun onChanged(t: Event<T>?) {
        val event = t ?: return
        event.handleEvent(eventHandler)
    }
}