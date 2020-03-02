package xyz.do9core.newsapplication.util.crashreporter

data class Alert(
    val errorLevel: Int,
    val message: String,
    val remark: String
)