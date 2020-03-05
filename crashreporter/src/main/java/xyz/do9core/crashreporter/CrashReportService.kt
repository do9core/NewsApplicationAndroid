package xyz.do9core.crashreporter

import retrofit2.http.POST

internal interface CrashReportService {

    @POST("/crash_report")
    suspend fun postCrashReport(errorLevel: Int, message: String, remark: String)
}
