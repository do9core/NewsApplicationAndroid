package xyz.do9core.liveeventbus.eventbus

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import xyz.do9core.liveeventbus.subject.SubjectLiveData

class LiveEventBusOperationDsl internal constructor(
    val eventBus: LiveEventBus,
    val key: LiveEventBus.Key
) {
    /**
     * [LiveEventBus.subject]
     * */
    inline fun <reified T : LiveEventBus.Event> subject() = eventBus.subject<T>(key)

    /**
     * [LiveEventBus.get]
     * */
    inline fun <reified T : LiveEventBus.Event> with() = eventBus.get<T>(key)

    /**
     * [SubjectLiveData.post]
     * */
    inline fun <reified T : LiveEventBus.Event> post(event: T) = eventBus.post(event, key)

    /**
     * [SubjectLiveData.postNow]
     * */
    @MainThread
    inline fun <reified T : LiveEventBus.Event> postNow(event: T) = eventBus.postNow(event, key)

    /**
     * [SubjectLiveData.postSticky]
     * */
    inline fun <reified T : LiveEventBus.Event> postSticky(event: T) =
        eventBus.postSticky(event, key)

    /**
     * [SubjectLiveData.postStickyNow]
     * */
    @MainThread
    inline fun <reified T : LiveEventBus.Event> postStickyNow(event: T) =
        eventBus.postStickyNow(event, key)

    @MainThread
    inline fun <reified T : LiveEventBus.Event> register(
        lifecycleOwner: LifecycleOwner,
        noinline subscriber: (T) -> Unit
    ) = eventBus.register(lifecycleOwner, key, subscriber)

    companion object {

        fun build(eventBus: LiveEventBus, key: LiveEventBus.Key): LiveEventBusOperationDsl {
            return LiveEventBusOperationDsl(eventBus, key)
        }
    }
}