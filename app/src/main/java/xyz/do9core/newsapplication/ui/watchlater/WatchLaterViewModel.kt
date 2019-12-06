package xyz.do9core.newsapplication.ui.watchlater

import androidx.lifecycle.*
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import xyz.do9core.newsapplication.util.LiveEvent
import xyz.do9core.newsapplication.util.LiveTrigger
import xyz.do9core.newsapplication.util.convertSource
import java.security.InvalidParameterException

class WatchLaterViewModel(
    private val app: NewsApplication
) : AndroidViewModel(app), ArticleClickHandler {

    private val loadTrigger = LiveTrigger()

    val showBrowserEvent = LiveEvent<String>()
    val watchLaterArticles = loadTrigger.convertSource {
        liveData(viewModelScope.coroutineContext) {
            val articles = app.database.articleDao().getWatchLaterArticles()
            emit(articles)
        }
    }

    override fun onClick(article: Article) {
        showBrowserEvent.event(article.url)
    }

    class Factory(
        private val application: NewsApplication
    ) : ViewModelProvider.AndroidViewModelFactory(application) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(WatchLaterViewModel::class.java)) {
                return WatchLaterViewModel(application) as T
            }
            throw InvalidParameterException()
        }
    }
}