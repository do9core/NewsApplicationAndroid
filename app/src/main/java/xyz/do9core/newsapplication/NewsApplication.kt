package xyz.do9core.newsapplication

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.data.remote.NewsService
import xyz.do9core.newsapplication.util.LogSource
import xyz.do9core.newsapplication.util.LogUtils

class NewsApplication : Application() {

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        val dataModule = module {
            single {
                val populateCallback = object : RoomDatabase.Callback() {
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
                val database =
                    Room.databaseBuilder(androidContext(), AppDatabase::class.java, "AppDB")
                        .addCallback(populateCallback)
                        .build()
                GlobalScope.launch(Dispatchers.IO) {
                    database.runInTransaction { Unit }
                }
                return@single database
            }
            single {
                Retrofit.Builder()
                    .baseUrl("https://newsapi.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            single { get<Retrofit>().create<NewsService>() }
        }

        val koinModules = listOf(dataModule)
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(koinModules)
        }
    }
}