package xyz.do9core.newsapplication.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, block: (T?) -> Unit) =
    liveData.observe(this, Observer(block))