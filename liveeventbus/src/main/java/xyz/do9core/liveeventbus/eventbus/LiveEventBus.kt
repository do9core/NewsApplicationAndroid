package xyz.do9core.liveeventbus.eventbus

import xyz.do9core.liveeventbus.subject.SubjectLiveData
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias Subject = SubjectLiveData<*>
private typealias KeyPair = Pair<KClass<*>, LiveEventBus.Key>

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
    abstract fun <T : Any> with(dataType: KClass<T>, key: Key = DefaultKey): SubjectLiveData<T>

    // 用companion object作为默认实现
    companion object : LiveEventBus() {

        // 使用 (数据类型, Key接口) 的元组作为Subject的索引
        private val subjects = ConcurrentHashMap<KeyPair, Subject>()

        override fun <T : Any> subject(dataType: KClass<T>, key: Key) {
            val keyPair = Pair(dataType, key)
            subjects.putIfAbsent(keyPair, SubjectLiveData<T>())
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any> with(dataType: KClass<T>, key: Key): SubjectLiveData<T> {
            val keyPair = Pair(dataType, key)
            val subject = subjects[keyPair]
            require(subject != null) { "No subject with ${dataType.java.name} and $key has been registered." }
            return subject as SubjectLiveData<T>
        }
    }
}
