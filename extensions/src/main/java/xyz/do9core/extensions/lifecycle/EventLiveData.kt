@file:Suppress("FunctionName")

package xyz.do9core.extensions.lifecycle

import androidx.lifecycle.MutableLiveData

internal typealias EventLiveData<T> = MutableLiveData<Event<T>>

fun <T : Any> EventLiveData(init: T? = null): EventLiveData<T> =
    EventLiveData<T>().apply {
        if (init != null) {
            value = Event(init)
        }
    }

fun EventLiveData(): EventLiveData<Unit> = EventLiveData<Unit>()