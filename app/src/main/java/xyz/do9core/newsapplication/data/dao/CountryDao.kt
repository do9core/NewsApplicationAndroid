package xyz.do9core.newsapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import xyz.do9core.newsapplication.data.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertCountries(country: List<Country>)

    @Query("SELECT * FROM country")
    suspend fun getCountries(): List<Country>
}