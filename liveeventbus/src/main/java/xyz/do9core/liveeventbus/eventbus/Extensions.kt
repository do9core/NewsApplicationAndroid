package xyz.do9core.liveeventbus.eventbus

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner

/**
 * 注册一个具有[T]类型和[key]索引的Subject的LiveData
 * */
inline fun <reified T : LiveEventBus.Event> LiveEventBus.subject(
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey
) = subject(T::class, key)

/**
 * 获取一个具有[T]类型和[key]索引的Subject的LiveData
 * */
inline fun <reified T : LiveEventBus.Event> LiveEventBus.get(
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey
) = get(T::class, key)

/**
 * 发送非粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@AnyThread
inline fun <reified T : LiveEventBus.Event> LiveEventBus.post(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey
) = get(T::class, key).post(event)

/**
 * 发送非粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : LiveEventBus.Event> LiveEventBus.postNow(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey
) = get(T::class, key).postNow(event)

/**
 * 发送粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@AnyThread
inline fun <reified T : LiveEventBus.Event> LiveEventBus.postSticky(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey
) = get(T::class, key).postSticky(event)

/**
 * 发送粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : LiveEventBus.Event> LiveEventBus.postStickyNow(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey
) = get(T::class, key).postStickyNow(event)

@MainThread
inline fun <reified T : LiveEventBus.Event> LiveEventBus.register(
    lifecycleOwner: LifecycleOwner,
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey,
    noinline subscriber: (T) -> Unit
) = get(T::class, key).register(lifecycleOwner, subscriber)

/**
 * 便于对同一个[key]下的Subject进行连续多个操作
 * */
inline fun LiveEventBus.withKey(
    key: LiveEventBus.Key = LiveEventBus.Key.DefaultKey,
    crossinline block: LiveEventBusOperationDsl.() -> Unit
) = LiveEventBusOperationDsl.build(this, key).run(block)