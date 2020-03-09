package xyz.do9core.liveeventbus.bus

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import xyz.do9core.liveeventbus.subject.SubjectLiveData

inline fun <reified T : Any> LiveEventBus.subject(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = subject(T::class, key)

inline fun <reified T : Any> LiveEventBus.with(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key)

@Suppress("UNCHECKED_CAST")
@AnyThread
fun <T : Any> LiveEventBus.post(event: T, key: LiveEventBus.Key = LiveEventBus.DefaultKey) =
    (with(event::class, key) as SubjectLiveData<T>).post(event)

@Suppress("UNCHECKED_CAST")
@MainThread
fun <T : Any> LiveEventBus.postNow(event: T, key: LiveEventBus.Key = LiveEventBus.DefaultKey) =
    (with(event::class, key) as SubjectLiveData<T>).postNow(event)

@Suppress("UNCHECKED_CAST")
@AnyThread
fun <T : Any> LiveEventBus.postSticky(event: T, key: LiveEventBus.Key = LiveEventBus.DefaultKey) =
    (with(event::class, key) as SubjectLiveData<T>).postSticky(event)

@Suppress("UNCHECKED_CAST")
@MainThread
fun <T : Any> LiveEventBus.postStickyNow(event: T, key: LiveEventBus.Key = LiveEventBus.DefaultKey) =
    (with(event::class, key) as SubjectLiveData<T>).postSticky(event)

fun LiveEventBus.withKey(key: LiveEventBus.Key, block: KeyDelegate.() -> Unit) =
    KeyDelegate(this, key).run(block)