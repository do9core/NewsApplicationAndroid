package xyz.do9core.liveeventbus.subject

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class SubjectLiveData<T : Any> : LiveData<DataWrapper<T>>() {

    private class Subscriber<T : Any>(
        private val subscriber: (T) -> Unit
    ) : Observer<DataWrapper<T>> {

        private val version = System.currentTimeMillis()

        override fun onChanged(wrapper: DataWrapper<T>?) {
            wrapper?.get(version)?.let(subscriber)
        }
    }

    // 注册新的Subscriber到当前Subject
    @MainThread
    fun register(
        lifecycleOwner: LifecycleOwner,
        subscriber: (T) -> Unit
    ) = super.observe(lifecycleOwner, Subscriber(subscriber))

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