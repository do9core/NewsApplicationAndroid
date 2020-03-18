package xyz.do9core.liveeventbus.subject

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import xyz.do9core.liveeventbus.eventbus.LiveEventBus

class SubjectLiveData<T : LiveEventBus.Event> : LiveData<DataWrapper<T>>() {

    // Subscriber的Wrapper，用于进行版本校验
    private class SubscriberWrapper<T : LiveEventBus.Event>(
        private val subscriber: (T) -> Unit
    ) : Observer<DataWrapper<T>> {
        // 使用当前时间作为版本号
        private val version = System.currentTimeMillis()

        override fun onChanged(wrapper: DataWrapper<T>?) {
            // 根据版本号判断是否能够接受事件
            // 如果Subscriber的版本更早，则可以接收事件；否则不能接收，取得null
            wrapper?.get(version)?.let(subscriber)
        }
    }

    // 注册新的Subscriber到当前Subject
    @MainThread
    fun register(
        lifecycleOwner: LifecycleOwner,
        subscriber: (T) -> Unit
    ) = super.observe(lifecycleOwner, SubscriberWrapper(subscriber))

    // 发送粘性Event
    fun postSticky(event: T) = super.postValue(DataWrapper(event))

    // 发送粘性Event，并且立刻调度给Subscriber
    @MainThread
    fun postStickyNow(event: T) = super.setValue(DataWrapper(event))

    // 发送非粘性Event
    fun post(event: T) = super.postValue(DataWrapper(event, sticky = false))

    // 发送非粘性Event，并立刻调度给Subscriber
    @MainThread
    fun postNow(event: T) = super.postValue(DataWrapper(event, sticky = false))
}