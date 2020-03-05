package xyz.do9core.newsapplication.data.local

import xyz.do9core.newsapplication.data.base.NewsDataSource
import xyz.do9core.newsapplication.data.model.Headline

object LocalDataSource : NewsDataSource {

    override suspend fun fetchHeadline(
        country: String,
        category: String,
        query: String,
        pageSize: Int,
        page: Int
    ): Headline {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}