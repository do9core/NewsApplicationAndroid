package xyz.do9core.liveeventbus.eventbus

import androidx.annotation.AnyThread
import androidx.annotation.MainThread

/**
 * 注册一个具有[T]类型和[key]索引的Subject的LiveData
 * */
inline fun <reified T : Any> LiveEventBus.subject(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = subject(T::class, key)

/**
 * 获取一个具有[T]类型和[key]索引的Subject的LiveData
 * */
inline fun <reified T : Any> LiveEventBus.with(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key)

/**
 * 发送非粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@AnyThread
inline fun <reified T : Any> LiveEventBus.post(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).post(event)

/**
 * 发送非粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : Any> LiveEventBus.postNow(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).postNow(event)

/**
 * 发送粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@AnyThread
inline fun <reified T : Any> LiveEventBus.postSticky(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).postSticky(event)

/**
 * 发送粘性Event到具有[T]类型和[key]索引的Subject
 * */
@Suppress("UNCHECKED_CAST")
@MainThread
inline fun <reified T : Any> LiveEventBus.postStickyNow(
    event: T,
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key).postStickyNow(event)

/**
 * 便于对同一个[key]下的Subject进行连续多个操作
 * */
inline fun LiveEventBus.withKey(
    key: LiveEventBus.Key,
    crossinline block: LiveEventBusOperationDsl.() -> Unit
) = LiveEventBusOperationDsl.build(this, key).run(block)