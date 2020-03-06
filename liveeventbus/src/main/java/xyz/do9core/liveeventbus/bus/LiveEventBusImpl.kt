package xyz.do9core.liveeventbus.bus

import android.util.Log
import androidx.lifecycle.MutableLiveData
import xyz.do9core.liveeventbus.subject.SubjectLiveData
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias Subject = SubjectLiveData<*>
private typealias KeyPair = Pair<KClass<*>, LiveEventBus.Key>

internal class LiveEventBusImpl : LiveEventBus() {

    private val subjects = ConcurrentHashMap<KeyPair, Subject>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> subject(dataType: KClass<T>, key: Key) {
        val keyPair = Pair(dataType, key)
        if (subjects[keyPair] != null) {
            Log.w(
                LiveEventBus::class.qualifiedName,
                "Subject with ${dataType.java.name} and $key already registered. " +
                        "New subject replacing it. " +
                        "Please make sure this is what you want."
            )
        }
        subjects[keyPair] = SubjectLiveData<T>()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> with(dataType: KClass<T>, key: Key): MutableLiveData<T> {
        val keyPair = Pair(dataType, key)
        val subject = subjects[keyPair]
        require(subject != null) { "No subject with ${dataType.java.name} and $key has been registered." }
        return subject as MutableLiveData<T>
    }
}