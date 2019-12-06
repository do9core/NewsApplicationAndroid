package xyz.do9core.newsapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.do9core.newsapplication.data.dao.ArticleDao
import xyz.do9core.newsapplication.data.dao.CategoryDao
import xyz.do9core.newsapplication.data.dao.CountryDao
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.data.model.Source

@Database(
    entities = [Article::class, Country::class, Category::class, Source::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [Converters::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun countryDao(): CountryDao
    abstract fun categoryDao(): CategoryDao
}