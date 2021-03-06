package xyz.do9core.newsapplication.data.dao

import androidx.room.*
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.FavouriteArticle
import xyz.do9core.newsapplication.data.model.FavouriteIds

@Dao
interface ArticleDao {

    @Query("SELECT * FROM favouritearticle")
    suspend fun getFavourites(): List<FavouriteArticle>

    @Insert(entity = FavouriteIds::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(favouriteIds: FavouriteIds)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Query("DELETE FROM favouriteids")
    suspend fun clearFavourite()

    @Query("DELETE FROM Article")
    suspend fun clearArticles()

    @Query("DELETE FROM favouriteids WHERE favouriteids.articleUrl = :articleUrl")
    suspend fun deleteFavourite(articleUrl: String)

    @Transaction
    suspend fun saveFavouriteArticle(article: Article) {
        saveArticle(article)
        saveFavourite(FavouriteIds(article.url))
    }
}