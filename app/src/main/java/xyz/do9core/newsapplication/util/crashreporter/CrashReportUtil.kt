package xyz.do9core.newsapplication.util.crashreporter

import android.app.Application
import androidx.work.*
import java.util.concurrent.TimeUnit

object CrashReportUtil {

    private lateinit var workManager: WorkManager

    fun install(application: Application) {
        workManager = WorkManager.getInstance(application)
    }

    fun report(alert: Alert) {
        workManager.enqueue(buildWorkRequest(alert))
    }

    private fun defaultConstrains(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun Alert.toWorkData() = workDataOf(
        CrashReporter.KEY_ERROR_LEVEL to errorLevel,
        CrashReporter.KEY_MESSAGE to message,
        CrashReporter.KEY_REMARK to remark
    )

    private fun buildWorkRequest(alert: Alert) = OneTimeWorkRequestBuilder<CrashReporter>()
        .setInputData(alert.toWorkData())
        .setConstraints(defaultConstrains())
        .setBackoffCriteria(
            BackoffPolicy.EXPONENTIAL,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.SECONDS)
        .build()
}