package xyz.do9core.newsapplication.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey
    val url: String,
    val source: Source,
    val author: String? = null,
    val title: String,
    val description: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null
) {
    object Differ : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}