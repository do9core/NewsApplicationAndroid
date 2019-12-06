package xyz.do9core.newsapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Category(
    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String
) {
    object Business : Category("business")
    object Entertainment : Category("entertainment")
    object General : Category("general")
    object Health : Category("health")
    object Science : Category("science")
    object Sports : Category("sports")
    object Technology : Category("technology")

    companion object {
        fun values() = listOf(
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