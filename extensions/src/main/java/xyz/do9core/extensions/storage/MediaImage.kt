package xyz.do9core.extensions.storage

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore

// TODO: create builder
data class MediaImage(
    val mimeType: String,
    val imageUri: Uri
) {

    fun toContentValues(): ContentValues {
        return ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE,mimeType)
        }
    }
}