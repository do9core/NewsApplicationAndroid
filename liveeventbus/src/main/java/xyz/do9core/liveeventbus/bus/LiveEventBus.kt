package xyz.do9core.liveeventbus.bus

import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KClass

interface LiveEventBus {

    interface Key

    fun <T : Any> subject(dataType: KClass<T>, key: Key? = null)

    fun <T : Any> with(dataType: KClass<T>, key: Key? = null): MutableLiveData<T>

    companion object {

        private val defaultInstance = LiveEventBusImpl()

        fun default(): LiveEventBus = defaultInstance
    }
}
