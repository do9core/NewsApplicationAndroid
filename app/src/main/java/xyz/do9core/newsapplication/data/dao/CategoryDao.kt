package xyz.do9core.newsapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import xyz.do9core.newsapplication.data.model.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategories(country: List<Category>)

    @Query("SELECT * FROM category")
    suspend fun getCountries(): List<Category>
}