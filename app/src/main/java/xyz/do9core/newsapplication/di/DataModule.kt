package xyz.do9core.newsapplication.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.remote.NewsService

const val API_BASE_URL = "https://newsapi.org"

val DataModule = module {
    single { get<Retrofit>().create<NewsService>() } bind NewsService::class
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "AppDB").build() }
    single {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}