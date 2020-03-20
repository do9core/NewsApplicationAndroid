package xyz.do9core.extensions.storage

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import java.io.Closeable

class LocalStorage(context: Context) : Closeable {

    private var _context: Context? = context
    val context: Context = _context!!

    fun saveImage(mediaImage: MediaImage) {
        saveContent(mediaImage.toContentValues())
    }

    fun saveContent(content: ContentValues) {
        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    override fun close() {
        _context = null
    }
}
