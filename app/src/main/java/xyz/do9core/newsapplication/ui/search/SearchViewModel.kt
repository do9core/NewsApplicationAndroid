package xyz.do9core.newsapplication.ui.search

import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.snakydesign.livedataextensions.emptyLiveData
import com.snakydesign.livedataextensions.startWith
import xyz.do9core.extensions.lifecycle.Event
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country

class SearchViewModel : ViewModel() {

    private val dataFactory = MutableLiveData<HeadlineSourceFactory>()
    private val dataSource = dataFactory.switchMap { it.dataSource }
    private val networkState = dataSource.switchMap { it.networkState }
    val searchResult = dataFactory.switchMap { it.toLiveData(10) }
    val isLoading = networkState.map { it.isLoading }.startWith(false)
    val noResult = networkState.map { it.isError }.startWith(false)

    val queryText = MutableLiveData<String>()
    val showArticleEvent = emptyLiveData<Event<Article>>()

    fun executeQuery(category: Category?, country: Country?) {
        val query = queryText.value ?: ""
        val sourceFactory = HeadlineSourceFactory(
            coroutineScope = viewModelScope,
            category = category,
            country = country,
            query = query
        )
        dataFactory.value = sourceFactory
    }

    fun showArticle(article: Article) = showArticleEvent.call(article)
}