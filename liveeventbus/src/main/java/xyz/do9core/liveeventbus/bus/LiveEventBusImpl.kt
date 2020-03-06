package xyz.do9core.liveeventbus.bus

import android.util.Log
import androidx.lifecycle.MutableLiveData
import xyz.do9core.liveeventbus.subject.SubjectLiveData
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias DataType = KClass<*>
private typealias Subject = SubjectLiveData<*>
private typealias SubjectMap = MutableMap<LiveEventBus.Key, Subject>

internal class LiveEventBusImpl : LiveEventBus {

    private val subjects = ConcurrentHashMap<DataType, SubjectMap>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> subject(dataType: KClass<T>, key: LiveEventBus.Key?) {
        val map = subjects.getOrPut(dataType) { ConcurrentHashMap() }
        val actualKey = key ?: LiveEventBusImpl
        Log.w(
            LiveEventBus::class.qualifiedName,
            "Subject with ${dataType.java.name} and $key already registered. " +
                    "New subject replacing it. " +
                    "Please make sure this is what you want."
        )
        map[actualKey] = SubjectLiveData<T>()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> with(dataType: KClass<T>, key: LiveEventBus.Key?): MutableLiveData<T> {
        val actualKey = key ?: LiveEventBusImpl
        val map = subjects[dataType]
        require(map != null) { "No subject with ${dataType.java.name} has been registered." }
        val subject = map[actualKey]
        require(subject != null) { "No subject with ${dataType.java.name} and $key has been registered." }
        return subject as MutableLiveData<T>
    }

    private companion object : LiveEventBus.Key
}