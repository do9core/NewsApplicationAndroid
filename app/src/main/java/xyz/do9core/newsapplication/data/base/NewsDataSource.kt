package xyz.do9core.newsapplication.data.base

import xyz.do9core.newsapplication.data.model.Headline

interface NewsDataSource {

    suspend fun getHeadline(
        country: String,
        category: String,
        query: String,
        pageSize: Int,
        page: Int
    ): Headline

}