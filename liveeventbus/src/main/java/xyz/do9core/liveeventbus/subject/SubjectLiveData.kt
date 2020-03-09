package xyz.do9core.liveeventbus.subject

import androidx.annotation.MainThread
import androidx.lifecycle.FlexLiveData
import androidx.lifecycle.LifecycleOwner
import xyz.do9core.liveeventbus.subscriber.Subscriber

abstract class SubjectLiveData<T : Any> : FlexLiveData<T>() {

    @MainThread
    fun register(
        lifecycleOwner: LifecycleOwner,
        subscriber: Subscriber<T>
    ) = super.observe(lifecycleOwner, subscriber)

    fun postSticky(event: T) = super.postValue(event)

    @MainThread
    fun postStickyNow(event: T) = super.setValue(event)

    abstract fun post(event: T)

    @MainThread
    abstract fun postNow(event: T)
}