package xyz.do9core.extensions.lifecycle

import androidx.lifecycle.Observer

class EventObserver<T : Any>(
    private val block: (T) -> Unit
) : Observer<Event<T>> {

    override fun onChanged(t: Event<T>?) {
        t?.get()?.let { block(it) }
    }
}