package xyz.do9core.liveeventbus.bus

import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KClass

/**
 * 使用LiveData的EventBus，得益于LiveData和Lifecycle可以不需要手动解注册来使用
 * */
abstract class LiveEventBus {

    /**
     * 特定数据类型下的Subject的索引
     * */
    interface Key

    /**
     * Subject的默认索引
     * */
    object DefaultKey : Key

    /**
     * 将一个具有[dataType]类型和[key]索引的新Subject添加到当前的EventBus中
     * */
    abstract fun <T : Any> subject(dataType: KClass<T>, key: Key = DefaultKey)

    /**
     * 获取一个具有[dataType]类型和[key]索引的Subject的LiveData
     * */
    abstract fun <T : Any> with(dataType: KClass<T>, key: Key = DefaultKey): MutableLiveData<T>

    companion object {

        /**
         * 默认的EventBus单例
         * */
        val Default: LiveEventBus = LiveEventBusImpl()
    }
}
