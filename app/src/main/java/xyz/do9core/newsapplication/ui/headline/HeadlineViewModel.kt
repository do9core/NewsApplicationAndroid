package xyz.do9core.newsapplication.ui.headline

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.toLiveData
import kotlinx.coroutines.launch
import xyz.do9core.extensions.lifecycle.EventLiveData
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.ui.common.ArticleClickedListener

class HeadlineViewModel(
    category: Category,
    private val appContext: Context,
    private val database: AppDatabase
) : ViewModel() {

    private val sourceFactory =
        HeadlineSourceFactory(viewModelScope, category, Country.UnitedStates)
    private val loadTrigger = MutableLiveData<Boolean>()

    val articleClicked: ArticleClickedListener = { showArticleEvent.call(it) }
    val favouriteHandler: ArticleClickedListener = ::saveToFavourite
    val watchLaterHandler: ArticleClickedListener = ::saveToWatchLater
    val saveImageHandler: ArticleClickedListener = ::saveImage

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
    val showArticleEvent = EventLiveData<Article>()
    val messageSnackbarEvent = EventLiveData<Int>()
    val errorSnackbarEvent = EventLiveData<String>()
    val imageSavedEvent = EventLiveData<Int>()

    @JvmOverloads
    fun loadArticles(forceReload: Boolean = false) = loadTrigger.postValue(forceReload)

    private fun saveToFavourite(article: Article) {
        viewModelScope.launch {
            try {
                database.articleDao().saveFavouriteArticle(article)
                messageSnackbarEvent.call(R.string.app_save_favourite_success)
            } catch (e: Exception) {
                errorSnackbarEvent.call(e.message.orEmpty())
            }
        }
    }

    private fun saveToWatchLater(article: Article) {
        viewModelScope.launch {
            try {
                database.articleDao().saveWatchLaterArticle(article)
                messageSnackbarEvent.call(R.string.app_save_watch_later_success)
            } catch (e: Exception) {
                errorSnackbarEvent.call(e.message.orEmpty())
            }
        }
    }

    private fun saveImage(article: Article) {
        TODO()
    }
}