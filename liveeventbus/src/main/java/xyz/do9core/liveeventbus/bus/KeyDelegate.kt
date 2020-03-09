package xyz.do9core.liveeventbus.bus

data class KeyDelegate internal constructor(
    private val eventBus: LiveEventBus,
    val key: LiveEventBus.Key
) {
    fun <T : Any> post(event: T) = eventBus.post(event, key)
    fun <T : Any> postNow(event: T) = eventBus.postNow(event, key)
    fun <T : Any> postSticky(event: T) = eventBus.postSticky(event, key)
    fun <T : Any> postStickyNow(event: T) = eventBus.postStickyNow(event, key)
}