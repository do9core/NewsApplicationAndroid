package xyz.do9core.newsapplication.data.repository

import xyz.do9core.newsapplication.data.base.NewsDataSource
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.data.model.Headline

class NewsRepository(
    private val remoteSource: NewsDataSource,
    private val localSource: NewsDataSource
) {

    suspend fun fetchHeadline(
        country: Country,
        category: Category,
        query: String = "",
        pageSize: Int = 20,
        page: Int = 0,
        // TODO: update to false when finished local data source
        forceReload: Boolean = true
    ): Headline {
        return if (forceReload) {
            // TODO: refresh local source
            remoteSource.fetchHeadline(
                country = country.code,
                category = category.title,
                query = query,
                pageSize = pageSize,
                page = page
            )
        } else {
            localSource.fetchHeadline(
                country = country.code,
                category = category.title,
                query = query,
                pageSize = pageSize,
                page = page
            )
        }
    }
}
