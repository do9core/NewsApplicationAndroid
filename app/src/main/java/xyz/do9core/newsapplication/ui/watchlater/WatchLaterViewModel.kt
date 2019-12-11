package xyz.do9core.newsapplication.ui.watchlater

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import xyz.do9core.newsapplication.util.LiveEvent
import java.security.InvalidParameterException

class WatchLaterViewModel(
    private val app: NewsApplication
) : AndroidViewModel(app), ArticleClickHandler {

    val showBrowserEvent = LiveEvent<Article>()
    val watchLaterArticles = liveData {
        val watchLaterArticles = app.database.articleDao().getWatchLater()
        val articles = watchLaterArticles.map { it.article }
        emit(articles)
    }

    override fun onClick(article: Article) = showBrowserEvent.event(article)

    class Factory(
        private val application: NewsApplication
    ) : ViewModelProvider.AndroidViewModelFactory(application) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WatchLaterViewModel::class.java)) {
                return WatchLaterViewModel(application) as T
            }
            throw InvalidParameterException()
        }
    }
}