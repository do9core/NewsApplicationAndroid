package xyz.do9core.newsapplication.data.workers

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toFile
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.net.URL
import java.util.*

class SaveImageWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            val imageUrl = inputData.getString(KEY_IMAGE_URL)
            val resolver = applicationContext.contentResolver
            val collectionPath = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                MediaStore.VOLUME_EXTERNAL
            } else {
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            }
            val collection = MediaStore.Images.Media.getContentUri(collectionPath)
            val fileName = UUID.randomUUID().toString()
            val imageDetail = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            }
            val newFile = resolver.insert(collection, imageDetail)
            newFile!!.toFile().outputStream().use { output ->
                URL(imageUrl).openStream()!!.use { input ->
                    input.copyTo(output)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {

        const val KEY_IMAGE_URL = "SaveImageWorker.KEY_IMAGE_URL"
    }
}