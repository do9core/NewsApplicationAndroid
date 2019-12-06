package xyz.do9core.newsapplication

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.util.LogSource
import xyz.do9core.newsapplication.util.LogUtils

class NewsApplication : Application() {

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        val populateCallback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch(Dispatchers.IO) {
                    LogUtils.debug(LogSource.DB, "Populating application database.")
                    database.countryDao().insertCountries(Country.values())
                    database.categoryDao().insertCategories(Category.values())
                    LogUtils.debug(LogSource.DB, "Database populated.")
                }
            }
        }

        database = Room
            .databaseBuilder(this, AppDatabase::class.java, "AppDB")
            .addCallback(populateCallback)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            database.runInTransaction { Unit }
        }
    }
}