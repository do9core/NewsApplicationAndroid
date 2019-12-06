package xyz.do9core.newsapplication.data.repository

import xyz.do9core.newsapplication.data.base.NewsDataSource

class NewsRepository(
    private val localSource: NewsDataSource,
    private val remoteSource: NewsDataSource
) {

}