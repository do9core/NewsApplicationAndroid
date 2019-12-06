package xyz.do9core.newsapplication.ui.headline

import androidx.lifecycle.*
import androidx.paging.toLiveData
import kotlinx.coroutines.launch
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.util.LiveEvent
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import java.security.InvalidParameterException

class HeadlineViewModel(
    category: Category,
    private val app: NewsApplication
) : ViewModel() {

    private val sourceFactory = HeadlineSourceFactory(viewModelScope, category, Country.UnitedStates)
    private val loadTrigger = MutableLiveData<Boolean>()
    val itemClickHandler = object : ArticleClickHandler {
        override fun onClick(article: Article) {
            showArticleEvent.event(article)
        }
    }
    val favouriteHandler = object : ArticleClickHandler {
        override fun onClick(article: Article) {
            viewModelScope.launch {
                val favArticle = article.copy().apply {
                    page = article.page
                    categoryName = article.categoryName
                    countryCode = article.countryCode
                    isFavourite = true
                }
                try {
                    app.database.articleDao().saveArticle(favArticle)
                    messageSnackbarEvent.postEvent(R.string.app_save_favourite_success)
                } catch (e: Exception) {
                    errorSnackbarEvent.postEvent(e.message)
                }
            }
        }
    }
    val watchLaterHandler = object : ArticleClickHandler {
        override fun onClick(article: Article) {
            viewModelScope.launch {
                val watchLaterArticle = article.copy().apply {
                    page = article.page
                    categoryName = article.categoryName
                    countryCode = article.countryCode
                    isFavourite = article.isFavourite
                    isWatchLater = true
                }
                try {
                    app.database.articleDao().saveArticle(watchLaterArticle)
                    messageSnackbarEvent.postEvent(R.string.app_save_watch_later_success)
                } catch (e: Exception) {
                    errorSnackbarEvent.postEvent(e.message)
                }
            }
        }
    }

    val articles = loadTrigger.switchMap {
        liveData {
            // sourceFactory.fromLocal = it
            val source = sourceFactory.toLiveData(20)
            emitSource(source)
        }
    }
    val networkState = sourceFactory.dataSource.switchMap { it.networkState }
    val isRefreshing = networkState.map { it.isLoading }
    val hasNetworkError = networkState.map { it.isError }
    val networkErrorMessage = networkState.map { (it as? LoadResult.Error)?.msg }
    val showArticleEvent = LiveEvent<Article>()
    val messageSnackbarEvent = LiveEvent<Int>()
    val errorSnackbarEvent = LiveEvent<String?>()

    @JvmOverloads
    fun loadArticles(forceReload: Boolean = false) = loadTrigger.postValue(forceReload)

    class Factory(
        private val category: Category,
        private val application: NewsApplication
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HeadlineViewModel::class.java)) {
                return HeadlineViewModel(category, application) as T
            }
            throw InvalidParameterException("This factory cannot create ${modelClass.simpleName} instance.")
        }
    }
}