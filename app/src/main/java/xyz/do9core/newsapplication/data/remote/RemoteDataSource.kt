package xyz.do9core.newsapplication.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.do9core.newsapplication.data.base.NewsDataSource
import xyz.do9core.newsapplication.data.model.Headline

object RemoteDataSource : NewsDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(NewsService::class.java)

    // ------------------------------------------------------------------

    override suspend fun getHeadline(
        country: String,
        category: String,
        query: String,
        pageSize: Int,
        page: Int
    ): Headline = withContext(Dispatchers.IO) {
        return@withContext service.getHeadlines(country, category, query, pageSize, page)
    }

    // ------------------------------------------------------------------

    private object Constants {
        const val BASE_URL = "https://newsapi.org"
    }
}