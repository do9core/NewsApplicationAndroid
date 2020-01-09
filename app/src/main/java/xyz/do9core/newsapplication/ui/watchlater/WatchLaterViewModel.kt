package xyz.do9core.newsapplication.ui.watchlater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.snakydesign.livedataextensions.emptyLiveData
import org.koin.core.KoinComponent
import org.koin.core.inject
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import xyz.do9core.newsapplication.util.Event
import xyz.do9core.newsapplication.util.event
import java.security.InvalidParameterException

class WatchLaterViewModel(private val database: AppDatabase) : ViewModel(), ArticleClickHandler {

    val showBrowserEvent = emptyLiveData<Event<Article>>()
    val watchLaterArticles = liveData {
        val watchLaterArticles = database.articleDao().getWatchLater()
        val articles = watchLaterArticles.map { it.article }
        emit(articles)
    }

    override fun onClick(article: Article) = showBrowserEvent.event(article)

    class Factory : ViewModelProvider.NewInstanceFactory(), KoinComponent {

        private val database by inject<AppDatabase>()

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WatchLaterViewModel::class.java)) {
                return WatchLaterViewModel(database) as T
            }
            throw InvalidParameterException()
        }
    }
}