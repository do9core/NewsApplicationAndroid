package xyz.do9core.newsapplication.data.dao

import androidx.room.*
import xyz.do9core.newsapplication.data.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article WHERE favourite = 1")
    suspend fun getFavouriteArticles(): List<Article>

    @Query("SELECT * FROM article WHERE watch_later = 1")
    suspend fun getWatchLaterArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Query("DELETE FROM article WHERE favourite = 1")
    suspend fun clearFavourites()
}