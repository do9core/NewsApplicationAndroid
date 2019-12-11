package xyz.do9core.newsapplication.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import xyz.do9core.newsapplication.util.LiveEvent
import xyz.do9core.newsapplication.util.LiveTrigger
import xyz.do9core.newsapplication.util.convertSource
import java.security.InvalidParameterException

class FavouriteViewModel(
    private val app: NewsApplication
) : ViewModel(), ArticleClickHandler {

    private val reloadTrigger = LiveTrigger()
    val favArticles = reloadTrigger.convertSource {
        liveData {
            val favArticles = app.database.articleDao().getFavourites()
            val articles = favArticles.map { it.article }
            emit(articles)
        }
    }
    val showBrowserEvent = LiveEvent<Article>()

    fun clearFavourites() {
        viewModelScope.launch {
            app.database.articleDao().clearFavourite()
            loadFavourites()
        }
    }

    fun loadFavourites() = reloadTrigger.signal()

    override fun onClick(article: Article) {
        showBrowserEvent.event(article)
    }

    class Factory(
        private val application: NewsApplication
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                return FavouriteViewModel(app = application) as T
            }
            throw InvalidParameterException(
                "This factory can only create ${FavouriteViewModel::class.java.simpleName}."
            )
        }
    }
}