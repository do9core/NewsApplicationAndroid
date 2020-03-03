package xyz.do9core.crashreporter

data class Report(
    val errorLevel: Int,
    val message: String,
    val remark: String
) {

    companion object {
        const val ALERT_LEVEL_DEBUG = 0
        const val ALERT_LEVEL_INFO = 1
        const val ALERT_LEVEL_WARNING = 2
        const val ALERT_LEVEL_ERROR = 3
        const val ALERT_LEVEL_FATAL = 4
    }
}