package xyz.do9core.liveeventbus.subject

// SubjectLiveData的数据信息Wrapper，即一个事件对象，主要提供粘性/非粘性事件的处理功能
data class DataWrapper<out T : Any> internal constructor(
    private val data: T,
    private val sticky: Boolean = true
) {
    // 使用当前时间作为版本号
    private val version: Long = System.currentTimeMillis()

    fun get(subscriberVersion: Long): T? {
        return when {
            // 在粘性情况下，无论subscriber版本如何，直接返回数据
            sticky -> data
            // 非粘性情况下，只有subscriber版本早于事件版本，才返回数据
            subscriberVersion <= version -> data
            else -> null
        }
    }
}