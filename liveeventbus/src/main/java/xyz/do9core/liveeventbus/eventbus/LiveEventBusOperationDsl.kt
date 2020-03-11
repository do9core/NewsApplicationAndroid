package xyz.do9core.liveeventbus.eventbus

import xyz.do9core.liveeventbus.subject.SubjectLiveData

class LiveEventBusOperationDsl internal constructor(
    val eventBus: LiveEventBus,
    val key: LiveEventBus.Key
) {
    /**
     * [LiveEventBus.subject]
     * */
    inline fun <reified T : Any> subject() = eventBus.subject<T>(key)

    /**
     * [LiveEventBus.with]
     * */
    inline fun <reified T : Any> with() = eventBus.with<T>(key)

    /**
     * [SubjectLiveData.post]
     * */
    inline fun <reified T : Any> post(event: T) = eventBus.post(event, key)

    /**
     * [SubjectLiveData.postNow]
     * */
    inline fun <reified T : Any> postNow(event: T) = eventBus.postNow(event, key)

    /**
     * [SubjectLiveData.postSticky]
     * */
    inline fun <reified T : Any> postSticky(event: T) = eventBus.postSticky(event, key)

    /**
     * [SubjectLiveData.postStickyNow]
     * */
    inline fun <reified T : Any> postStickyNow(event: T) = eventBus.postStickyNow(event, key)

    companion object {

        fun build(eventBus: LiveEventBus, key: LiveEventBus.Key): LiveEventBusOperationDsl {
            return LiveEventBusOperationDsl(eventBus, key)
        }
    }
}