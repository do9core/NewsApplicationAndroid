package xyz.do9core.extensions.lifecycle

import androidx.annotation.AnyThread
import androidx.annotation.MainThread

class Event<out T : Any>(private val param: T) {

    var handled = false
        private set

    fun get(): T? {
        if (!handled) {
            handled = true
            return param
        }
        return null
    }

    fun peek(): T = param
}

@MainThread
fun EventLiveData<Unit>.call() {
    this.value = Event(Unit)
}

@AnyThread
fun EventLiveData<Unit>.post() {
    this.postValue(Event(Unit))
}

@MainThread
fun <T : Any> EventLiveData<T>.call(value: T) {
    this.value = Event(value)
}

@AnyThread
fun <T : Any> EventLiveData<T>.post(value: T) {
    this.postValue(Event(value))
}