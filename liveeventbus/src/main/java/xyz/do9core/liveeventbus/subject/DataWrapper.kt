package xyz.do9core.liveeventbus.subject

data class DataWrapper<out T : Any> internal constructor(
    private val data: T,
    private val sticky: Boolean = true
) {
    private val version: Long = System.currentTimeMillis()

    fun get(subscriberVersion: Long): T? {
        return when {
            sticky -> data
            subscriberVersion <= version -> data
            else -> null
        }
    }
}