package xyz.do9core.extensions.storage

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File

inline fun Context.useLocalStorage(block: LocalStorage.() -> Unit) =
    LocalStorage(this).use(block)

fun canWriteExternalStorage(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

fun canReadExternalStorage(): Boolean {
    return Environment.getExternalStorageState() in
            setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
}

fun getAlbumStorageDir(context: Context, albumName: String): File {
    val file = File(context.getExternalFilesDir(
        Environment.DIRECTORY_PICTURES), albumName)
    if (!file.mkdirs()) {
        Log.e("StorageEx", "Dir not created")
    }
    return file
}