package xyz.do9core.newsapplication.ui.search

import androidx.lifecycle.*
import androidx.paging.toLiveData
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.util.LiveEvent

class SearchViewModel : ViewModel() {

    private val dataFactory = MutableLiveData<HeadlineSourceFactory>()
    private val dataSource = dataFactory.switchMap { it.dataSource }
    private val networkState = dataSource.switchMap { it.networkState }
    val searchResult = dataFactory.switchMap { it.toLiveData(10) }
    val refreshing = networkState.map { it.isLoading }

    val queryText = MutableLiveData<String>()
    val showArticleEvent = LiveEvent<Article>()

    fun executeQuery() {
        val query = queryText.value ?: ""
        val sourceFactory = HeadlineSourceFactory(
            coroutineScope = viewModelScope,
            category = Category.Technology,
            country = Country.UnitedStates,
            query = query
        )
        dataFactory.value = sourceFactory
    }

    fun showArticle(article: Article) = showArticleEvent.event(article)
}