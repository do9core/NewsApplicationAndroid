package xyz.do9core.newsapplication.data.dao

import androidx.room.*
import xyz.do9core.newsapplication.data.model.*

@Dao
interface ArticleDao {

    @Query("SELECT * FROM favouritearticle")
    suspend fun getFavourites(): List<FavouriteArticle>

    @Query("SELECT * FROM watchlaterarticle")
    suspend fun getWatchLater(): List<FavouriteArticle>

    @Insert(entity = FavouriteIds::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(favouriteIds: FavouriteIds)

    @Insert(entity = WatchLaterIds::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWatchLater(watchLaterIds: WatchLaterIds)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Query("DELETE FROM favouriteids")
    suspend fun clearFavourite()

    @Query("DELETE FROM watchlaterids")
    suspend fun clearWatchLater()

    @Query("DELETE FROM Article")
    suspend fun clearArticles()

    @Transaction
    suspend fun saveFavouriteArticle(article: Article) {
        saveArticle(article)
        saveFavourite(FavouriteIds(article.url))
    }

    @Transaction
    suspend fun saveWatchLaterArticle(article: Article) {
        saveArticle(article)
        saveWatchLater(WatchLaterIds(article.url))
    }
}