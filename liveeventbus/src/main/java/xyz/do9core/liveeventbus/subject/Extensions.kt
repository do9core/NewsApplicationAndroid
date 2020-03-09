package xyz.do9core.liveeventbus.subject

import androidx.lifecycle.LifecycleOwner
import xyz.do9core.liveeventbus.subscriber.Subscriber

inline fun <T : Any> SubjectLiveData<T>.register(
    lifecycleOwner: LifecycleOwner,
    crossinline block: (T) -> Unit
) = this.register(lifecycleOwner, Subscriber { block(it) })