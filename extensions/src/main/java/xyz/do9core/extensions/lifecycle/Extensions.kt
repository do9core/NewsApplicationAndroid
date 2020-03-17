package xyz.do9core.extensions.lifecycle

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T : Any> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline block: (T) -> Unit
) = liveData.observe(this, Observer { block(it) })

inline fun <T : Any> LifecycleOwner.observeNullable(
    liveData: LiveData<T?>,
    crossinline block: (T?) -> Unit
) = liveData.observe(this, Observer { block(it) })

inline fun <T : Any> LifecycleOwner.observeEvent(
    liveData: LiveData<Event<T>>,
    crossinline block: (T) -> Unit
) = liveData.observe(this, EventObserver { block(it) })

inline fun <T : Any> LiveData<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline block: (T) -> Unit
) = observe(lifecycleOwner, Observer { block(it) })

inline fun <T : Any> LiveData<T?>.observeNullable(
    lifecycleOwner: LifecycleOwner,
    crossinline block: (T?) -> Unit
) = observe(lifecycleOwner, Observer { block(it) })

@MainThread
fun <T : Any> EventLiveData<T>.call(param: T) {
    this.value = Event(param)
}

@AnyThread
fun <T : Any> EventLiveData<T>.post(param: T) = this.postValue(Event(param))

@MainThread
fun EventLiveData<Unit>.call() {
    this.value = Event(Unit)
}

@AnyThread
fun EventLiveData<Unit>.post() = this.postValue(Event(Unit))