package xyz.do9core.newsapplication.util.crashhandler

import retrofit2.http.POST

interface CrashReportService {

    @POST("/crash_report")
    suspend fun postCrashReport(errorLevel: Int, message: String, remark: String)
}
