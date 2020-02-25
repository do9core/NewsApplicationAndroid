package xyz.do9core.newsapplication.util.crashhandler

import android.app.Application
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.Retrofit
import retrofit2.create

class CrashReporter(
    context: Application,
    parameter: WorkerParameters
) : CoroutineWorker(context, parameter) {

    companion object {

        const val KEY_ERROR_LEVEL = "KEY_ERROR_LEVEL"
        const val KEY_MESSAGE = "KEY_MESSAGE"
        const val KEY_REMARK = "KEY_REMARK"

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://do9core.xyz/newsapplication")
            .build()
        private val service = retrofit.create<CrashReportService>()
    }

    override suspend fun doWork(): Result = try {
        val errorLevel = inputData.getInt(KEY_ERROR_LEVEL, 0)
        val message = inputData.getString(KEY_MESSAGE).orEmpty()
        val remark = inputData.getString(KEY_REMARK).orEmpty()
        service.postCrashReport(errorLevel, message, remark)
        Result.success()
    } catch (e: Exception) {
        Result.retry()
    }
}
