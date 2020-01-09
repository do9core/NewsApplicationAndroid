package xyz.do9core.newsapplication.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.viewObserve(liveData: LiveData<T>, handler: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner, Observer(handler))

fun <T : Any> Fragment.viewObserveEvent(eventLiveData: LiveData<Event<T>>, handler: (T) -> Unit) =
    viewLifecycleOwner.observeEvent(eventLiveData, handler)