package xyz.do9core.newsapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
open class Category(
    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String
) : Serializable {
    object Business : Category("business"), Serializable
    object Entertainment : Category("entertainment"), Serializable
    object General : Category("general"), Serializable
    object Health : Category("health"), Serializable
    object Science : Category("science"), Serializable
    object Sports : Category("sports"), Serializable
    object Technology : Category("technology"), Serializable

    companion object {
        fun values(): List<Category> = listOf(
            Business,
            Entertainment,
            General,
            Health,
            Science,
            Sports,
            Technology
        )
    }
}