package xyz.do9core.newsapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import xyz.do9core.newsapplication.data.base.NewsDataSource
import xyz.do9core.newsapplication.data.model.Headline

object RemoteDataSource : NewsDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<NewsService>()

    // ------------------------------------------------------------------

    override suspend fun fetchHeadline(
        country: String,
        category: String,
        query: String,
        pageSize: Int,
        page: Int
    ): Headline = service.getHeadline(country, category, query, pageSize, page)

    // ------------------------------------------------------------------

    private object Constants {
        const val BASE_URL = "https://newsapi.org"
    }
}