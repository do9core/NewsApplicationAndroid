package xyz.do9core.newsapplication.util

import android.util.Log

enum class LogSource(val tag: String) {
    DB("AppDatabase")
}

object LogUtils {

    fun debug(source: LogSource, message: String) = Log.d(source.tag, message)
}