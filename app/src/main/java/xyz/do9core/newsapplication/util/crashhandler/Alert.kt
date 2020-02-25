package xyz.do9core.newsapplication.util.crashhandler

data class Alert(
    val errorLevel: Int,
    val message: String,
    val remark: String
)