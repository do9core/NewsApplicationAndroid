package xyz.do9core.extensions.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T : Any> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline block: (T) -> Unit
) = liveData.observe(this, Observer { block(it) })

inline fun <T : Any> LifecycleOwner.observeNullable(
    liveData: LiveData<T?>,
    crossinline block: (T?) -> Unit
) = liveData.observe(this, Observer { block(it) })

inline fun <T : Any> LifecycleOwner.observeEvent(
    liveData: LiveData<Event<T>>,
    crossinline block: (T) -> Unit
) = liveData.observe(this, EventObserver { block(it) })

inline fun <T : Any> LiveData<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline block: (T) -> Unit
) = observe(lifecycleOwner, Observer { block(it) })

inline fun <T : Any> LiveData<T?>.observeNullable(
    lifecycleOwner: LifecycleOwner,
    crossinline block: (T?) -> Unit
) = observe(lifecycleOwner, Observer { block(it) })