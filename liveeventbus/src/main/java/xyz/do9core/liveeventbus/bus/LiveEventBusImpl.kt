package xyz.do9core.liveeventbus.bus

import android.util.Log
import androidx.lifecycle.MutableLiveData
import xyz.do9core.liveeventbus.subject.SubjectLiveData
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias Subject = SubjectLiveData<*>
private typealias KeyPair = Pair<KClass<*>, LiveEventBus.Key>

/**
 * [LiveEventBus]的默认实现
 * */
internal class LiveEventBusImpl : LiveEventBus() {

    private val subjects = ConcurrentHashMap<KeyPair, Subject>()

    override fun <T : Any> subject(dataType: KClass<T>, key: Key) {
        val keyPair = Pair(dataType, key)
        if (subjects[keyPair] != null) {
            /**
             * 默认情况下，当同类型和同key的EventBus存在时，不抛出异常而是替换之前的Subject
             * 这有助于在的Activity、Fragment等其他LifecycleOwner对象的作用域内替换Subject
             *
             * 但是为了防止错误利用这一特性，还是会输出警告信息
             * */
            Log.w(
                LiveEventBus::class.java.name,
                "Subject with ${dataType.java.name} and $key already registered. " +
                        "New subject replacing it. " +
                        "Please make sure this is what you want."
            )
        }
        subjects[keyPair] = SubjectLiveData<T>()
    }

    override fun <T : Any> with(dataType: KClass<T>, key: Key): MutableLiveData<T> {
        val keyPair = Pair(dataType, key)
        val subject = subjects[keyPair]
        require(subject != null) { "No subject with ${dataType.java.name} and $key has been registered." }
        @Suppress("UNCHECKED_CAST")
        return subject as MutableLiveData<T>
    }
}