package xyz.do9core.liveeventbus.bus

import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KClass

abstract class LiveEventBus {

    interface Key

    object DefaultKey : Key

    abstract fun <T : Any> subject(dataType: KClass<T>, key: Key = DefaultKey)

    abstract fun <T : Any> with(dataType: KClass<T>, key: Key = DefaultKey): MutableLiveData<T>

    companion object {

        val Default: LiveEventBus = LiveEventBusImpl()
    }
}
