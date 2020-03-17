package xyz.do9core.newsapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.do9core.newsapplication.data.dao.ArticleDao
import xyz.do9core.newsapplication.data.dao.CategoryDao
import xyz.do9core.newsapplication.data.dao.CountryDao
import xyz.do9core.newsapplication.data.model.*

@Database(
    entities = [
        Article::class,
        FavouriteIds::class,
        Country::class,
        Category::class,
        Source::class
    ],
    views = [
        FavouriteArticle::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun countryDao(): CountryDao
    abstract fun categoryDao(): CategoryDao
}