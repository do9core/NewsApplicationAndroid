package xyz.do9core.crashreporter

import android.app.Application
import androidx.work.*
import java.util.concurrent.TimeUnit

object CrashReportUtil {

    private lateinit var workManager: WorkManager

    @Throws(IllegalStateException::class)
    fun install(application: Application) {
        try {
            workManager = WorkManager.getInstance(application)
            val origin = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler { t, e ->
                Report(
                    errorLevel = Report.ALERT_LEVEL_FATAL,
                    message = e.message.toString(),
                    remark = t.toString()
                ).also { report(it) }
                origin?.uncaughtException(t, e)
            }
        } catch (e: Exception) {
            throw IllegalStateException("Crash reporter install failed!")
        }
    }

    fun report(report: Report) {
        workManager.enqueue(buildWorkRequest(report))
    }

    private fun defaultConstrains(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private fun Report.toWorkData() = workDataOf(
        CrashReporter.KEY_ERROR_LEVEL to errorLevel,
        CrashReporter.KEY_MESSAGE to message,
        CrashReporter.KEY_REMARK to remark
    )

    private fun buildWorkRequest(report: Report) = OneTimeWorkRequestBuilder<CrashReporter>()
        .setInputData(report.toWorkData())
        .setConstraints(defaultConstrains())
        .setBackoffCriteria(
            BackoffPolicy.EXPONENTIAL,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.SECONDS
        )
        .build()
}