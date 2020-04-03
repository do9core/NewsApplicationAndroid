package xyz.do9core.extensions.lifecycle

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
