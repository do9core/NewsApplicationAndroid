package xyz.do9core.extensions.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import xyz.do9core.extensions.lifecycle.Event
import xyz.do9core.extensions.lifecycle.EventObserver

inline fun <T : Any> Fragment.viewObserve(
    liveData: LiveData<T>,
    crossinline block: (T) -> Unit
) = liveData.observe(viewLifecycleOwner, Observer { block(it) })

inline fun <T : Any> Fragment.viewObserveNullable(
    liveData: LiveData<T?>,
    crossinline block: (T?) -> Unit
) = liveData.observe(viewLifecycleOwner, Observer { block(it) })

inline fun <T : Any> Fragment.viewObserveEvent(
    liveData: LiveData<Event<T>>,
    crossinline block: (T) -> Unit
) = liveData.observe(viewLifecycleOwner, EventObserver { block(it) })