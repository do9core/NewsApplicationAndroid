package xyz.do9core.extensions.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

class Event<out T : Any>(private val param: T) {

    var handled = false
        private set

    fun getParameter(): T? {
        if (!handled) {
            handled = true
            return param
        }
        return null
    }
}
