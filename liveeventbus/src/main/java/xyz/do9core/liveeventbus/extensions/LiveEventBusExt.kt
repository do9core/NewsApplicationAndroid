package xyz.do9core.liveeventbus.extensions

import xyz.do9core.liveeventbus.bus.LiveEventBus

inline fun <reified T : Any> LiveEventBus.subject(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = subject(T::class, key)

inline fun <reified T : Any> LiveEventBus.with(
    key: LiveEventBus.Key = LiveEventBus.DefaultKey
) = with(T::class, key)