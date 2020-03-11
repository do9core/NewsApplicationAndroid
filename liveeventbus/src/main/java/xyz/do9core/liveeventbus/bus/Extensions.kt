package xyz.do9core.liveeventbus.bus

import androidx.annotation.AnyThread
import androidx.annotation.MainThread

inline fun <reified T : Any> LiveEventBus.subject(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = subject(T::class, key)

inline fun <reified T : Any> LiveEventBus.with(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key)

@Suppress("UNCHECKED_CAST")
@AnyThread
inline fun <reified T : Any> LiveEventBus.post(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).post(event)

@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : Any> LiveEventBus.postNow(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).postNow(event)

@Suppress("UNCHECKED_CAST")
@AnyThread
inline fun <reified T : Any> LiveEventBus.postSticky(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).postSticky(event)

@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : Any> LiveEventBus.postStickyNow(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).postStickyNow(event)

fun LiveEventBus.withKey(key: LiveEventBus.Key, block: KeyDelegate.() -> Unit) =
    KeyDelegate(this, key).run(block)