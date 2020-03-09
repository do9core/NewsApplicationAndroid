package xyz.do9core.liveeventbus.subscriber

import androidx.lifecycle.Observer

class Subscriber<T : Any>(
    private val handler: (T) -> Unit
) : Observer<T> {

    override fun onChanged(t: T?) = t?.let(handler) ?: Unit
}