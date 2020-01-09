package xyz.do9core.newsapplication.data.remote

import org.koin.core.KoinComponent
import org.koin.core.inject
import xyz.do9core.newsapplication.data.base.NewsDataSource
import xyz.do9core.newsapplication.data.model.Headline

object RemoteDataSource : NewsDataSource, KoinComponent {

    private val service by inject<NewsService>()

    override suspend fun fetchHeadline(
        country: String,
        category: String,
        query: String,
        pageSize: Int,
        page: Int
    ): Headline = service.getHeadline(country, category, query, pageSize, page)
}