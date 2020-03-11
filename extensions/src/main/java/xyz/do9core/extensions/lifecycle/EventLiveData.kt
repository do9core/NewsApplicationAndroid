package xyz.do9core.extensions.lifecycle

import androidx.lifecycle.MutableLiveData

internal typealias EventLiveData<T> = MutableLiveData<Event<T>>

@Suppress("FunctionName")
fun EventLiveData(triggerNow: Boolean = false): EventLiveData<Unit> =
    EventLiveData<Unit>().apply {
        if (triggerNow) {
            value = Event(Unit)
        }
    }

@Suppress("FunctionName")
fun <T : Any> EventLiveData(initialValue: T? = null): EventLiveData<T> =
    EventLiveData<T>().apply {
        initialValue?.let { value = Event(it) }
    }