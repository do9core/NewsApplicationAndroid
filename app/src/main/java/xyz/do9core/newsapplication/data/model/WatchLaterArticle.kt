package xyz.do9core.newsapplication.data.model

import androidx.room.*

@DatabaseView(
    """
    SELECT wids.id, a.*
    FROM watchlaterids wids, article a
    WHERE wids.articleUrl = a.url
    """
)
class WatchLaterArticle(
    val id: Long,
    @Embedded
    val article: Article
)

@Entity
class WatchLaterIds(
    val articleUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)