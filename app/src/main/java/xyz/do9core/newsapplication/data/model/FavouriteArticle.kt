package xyz.do9core.newsapplication.data.model

import androidx.room.*

@DatabaseView(
    """
    SELECT fids.id, a.*
    FROM favouriteids fids, article a
    WHERE fids.articleUrl = a.url
    """
)
data class FavouriteArticle(
    val id: Long,
    @Embedded
    val article: Article
)

@Entity
data class FavouriteIds(
    val articleUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)