package xyz.do9core.liveeventbus.subject

import androidx.annotation.MainThread
import androidx.lifecycle.FlexLiveData
import androidx.lifecycle.LifecycleOwner
import xyz.do9core.liveeventbus.subscriber.Subscriber

abstract class SubjectLiveData<T : Any> : FlexLiveData<T>() {

    // 注册新的Subscriber到当前Subject
    @MainThread
    fun register(
        lifecycleOwner: LifecycleOwner,
        subscriber: Subscriber<T>
    ) = super.observe(lifecycleOwner, subscriber)

    // 发送粘性Event
    fun postSticky(event: T) = super.postValue(event)

    // 发送粘性Event，并且立刻调度给Subscriber
    @MainThread
    fun postStickyNow(event: T) = super.setValue(event)

    // 发送非粘性Event
    abstract fun post(event: T)

    // 发送非粘性Event，并立刻调度给Subscriber
    @MainThread
    abstract fun postNow(event: T)
}