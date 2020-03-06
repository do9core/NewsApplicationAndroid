package xyz.do9core.liveeventbus.extensions

import xyz.do9core.liveeventbus.bus.LiveEventBus

inline fun <reified T : Any> LiveEventBus.subject(key: LiveEventBus.Key? = null) =
    subject(T::class, key)

inline fun <reified T : Any> LiveEventBus.with(key: LiveEventBus.Key? = null) =
    with(T::class, key)