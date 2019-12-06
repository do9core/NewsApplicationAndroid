package xyz.do9core.newsapplication.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["title", "url"],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["title"],
            childColumns = ["category"]
        ),
        ForeignKey(
            entity = Country::class,
            parentColumns = ["code"],
            childColumns = ["country"]
        )
    ],
    indices = [
        Index(
            value = ["category"]
        ),
        Index(
            value = ["country"]
        )
    ]
)
data class Article (
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null
) {

    @ColumnInfo(name = "favourite")
    var isFavourite: Boolean = false

    @ColumnInfo(name = "watch_later")
    var isWatchLater: Boolean = false

    @ColumnInfo(name = "category")
    var categoryName: String = ""

    @ColumnInfo(name = "country")
    var countryCode: String = ""

    @ColumnInfo(name = "page")
    var page: Int = 0

    object Differ : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}