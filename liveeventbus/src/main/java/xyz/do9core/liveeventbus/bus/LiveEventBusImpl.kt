package xyz.do9core.liveeventbus.bus

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
        subjects.putIfAbsent(keyPair, SubjectLiveData<T>())
    }

    override fun <T : Any> with(dataType: KClass<T>, key: Key): MutableLiveData<T> {
        val keyPair = Pair(dataType, key)
        val subject = subjects[keyPair]
        require(subject != null) { "No subject with ${dataType.java.name} and $key has been registered." }
        @Suppress("UNCHECKED_CAST")
        return subject as MutableLiveData<T>
    }
}