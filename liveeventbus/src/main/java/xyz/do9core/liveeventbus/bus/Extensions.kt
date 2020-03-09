package xyz.do9core.liveeventbus.bus

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

inline fun <reified T : Any> LiveEventBus.subject(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = subject(T::class, key)

inline fun <reified T : Any> LiveEventBus.with(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key)

@Suppress("UNCHECKED_CAST")
@AnyThread
fun <T : Any> LiveEventBus.post(event: T, key: LiveEventBus.Key = LiveEventBus.DefaultKey) {
    (with(event::class, key) as MutableLiveData<T>).postValue(event)
}

@Suppress("UNCHECKED_CAST")
@MainThread
fun <T : Any> LiveEventBus.postNow(event: T, key: LiveEventBus.Key = LiveEventBus.DefaultKey) {
    (with(event::class, key) as MutableLiveData<T>).value = event
}

interface EventBusDelegate {

    fun <T : Any> post(event: T)

    fun <T : Any> postNow(event: T)
}

internal data class EventBusDelegateImpl(
    private val eventBus: LiveEventBus,
    val key: LiveEventBus.Key
) : EventBusDelegate {

    override fun <T : Any> post(event: T) = eventBus.post(event, key)

    override fun <T : Any> postNow(event: T) = eventBus.postNow(event, key)
}

fun LiveEventBus.withKey(key: LiveEventBus.Key, block: EventBusDelegate.() -> Unit) =
    EventBusDelegateImpl(this, key).run(block)