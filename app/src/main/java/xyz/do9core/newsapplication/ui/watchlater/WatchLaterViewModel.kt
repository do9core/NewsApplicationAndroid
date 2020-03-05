package xyz.do9core.newsapplication.ui.watchlater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.snakydesign.livedataextensions.emptyLiveData
import xyz.do9core.extensions.lifecycle.Event
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickedListener

class WatchLaterViewModel(private val database: AppDatabase) : ViewModel() {

    val showBrowserEvent = emptyLiveData<Event<Article>>()
    
    val articleClicked: ArticleClickedListener = { showBrowserEvent.call(it) }
    
    val watchLaterArticles = liveData {
        val watchLaterArticles = database.articleDao().getWatchLater()
        val articles = watchLaterArticles.map { it.article }
        emit(articles)
    }
}