package xyz.do9core.liveeventbus.bus

class KeyDelegate internal constructor(
    val eventBus: LiveEventBus,
    val key: LiveEventBus.Key
) {
    inline fun <reified T : Any> post(event: T) = eventBus.post(event, key)
    inline fun <reified T : Any> postNow(event: T) = eventBus.postNow(event, key)
    inline fun <reified T : Any> postSticky(event: T) = eventBus.postSticky(event, key)
    inline fun <reified T : Any> postStickyNow(event: T) = eventBus.postStickyNow(event, key)
}