package xyz.do9core.newsapplication.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import xyz.do9core.newsapplication.data.model.Source

object Converters {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun sourceToString(source: Source?): String? {
        return gson.toJson(source)
    }

    @TypeConverter
    @JvmStatic
    fun stringToSource(source: String?): Source? {
        return gson.fromJson(source, Source::class.java)
    }
}
