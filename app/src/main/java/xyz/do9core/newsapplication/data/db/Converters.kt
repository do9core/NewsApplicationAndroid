package xyz.do9core.newsapplication.data.db

import androidx.room.TypeConverter
import xyz.do9core.newsapplication.data.model.Source

object Converters {

    @TypeConverter
    @JvmStatic fun sourceToString(source: Source?): String? {
        source ?: return null
        return "SID: ${source.id}|SName: ${source.name}"
    }

    @TypeConverter
    @JvmStatic fun stringToSource(source: String?): Source? {
        source ?: return null
        val values = Regex("""SID: (.*?)\|SName: (.*)""").matchEntire(source)
        values ?: return null
        return Source(id = values.groupValues.first(), name = values.groupValues.last())
    }
}
