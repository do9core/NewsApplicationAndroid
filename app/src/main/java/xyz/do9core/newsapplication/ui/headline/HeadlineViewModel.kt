package xyz.do9core.newsapplication.ui.headline

import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.snakydesign.livedataextensions.emptyLiveData
import kotlinx.coroutines.launch
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import xyz.do9core.newsapplication.util.Event
import xyz.do9core.newsapplication.util.event
import java.security.InvalidParameterException

class HeadlineViewModel(
    category: Category,
    private val app: NewsApplication
) : ViewModel() {

    private val sourceFactory =
        HeadlineSourceFactory(viewModelScope, category, Country.UnitedStates)
    private val loadTrigger = MutableLiveData<Boolean>()
    val itemClickHandler = object : ArticleClickHandler {
        override fun onClick(article: Article) {
            showArticleEvent.event(article)
        }
    }
    val favouriteHandler = object : ArticleClickHandler {
        override fun onClick(article: Article) {
            viewModelScope.launch {
                try {
                    app.database.articleDao().saveFavouriteArticle(article)
                    messageSnackbarEvent.event(R.string.app_save_favourite_success)
                } catch (e: Exception) {
                    errorSnackbarEvent.event(e.message.orEmpty())
                }
            }
        }
    }
    val watchLaterHandler = object : ArticleClickHandler {
        override fun onClick(article: Article) {
            viewModelScope.launch {
                try {
                    app.database.articleDao().saveWatchLaterArticle(article)
                    messageSnackbarEvent.event(R.string.app_save_watch_later_success)
                } catch (e: Exception) {
                    errorSnackbarEvent.event(e.message.orEmpty())
                }
            }
        }
    }

    val articles = loadTrigger.switchMap {
        liveData {
            // sourceFactory.fromLocal = it
            val source = sourceFactory.toLiveData(10)
            emitSource(source)
        }
    }
    val networkState = sourceFactory.dataSource.switchMap { it.networkState }
    val isRefreshing = networkState.map { it.isLoading }
    val hasNetworkError = networkState.map { it.isError }
    val networkErrorMessage = networkState.map { (it as? LoadResult.Error)?.msg }
    val showArticleEvent = emptyLiveData<Event<Article>>()
    val messageSnackbarEvent = emptyLiveData<Event<Int>>()
    val errorSnackbarEvent = emptyLiveData<Event<String>>()

    @JvmOverloads
    fun loadArticles(forceReload: Boolean = false) = loadTrigger.postValue(forceReload)

    class Factory(
        private val category: Category,
        private val application: NewsApplication
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HeadlineViewModel::class.java)) {
                return HeadlineViewModel(category, application) as T
            }
            throw InvalidParameterException("This factory cannot create ${modelClass.simpleName} instance.")
        }
    }
}