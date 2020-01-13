package xyz.do9core.newsapplication.ui.favourite

import androidx.lifecycle.*
import com.snakydesign.livedataextensions.emptyLiveData
import com.snakydesign.livedataextensions.switchMap
import kotlinx.coroutines.launch
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickHandler
import xyz.do9core.newsapplication.util.Event
import xyz.do9core.newsapplication.util.event
import xyz.do9core.newsapplication.util.trigger

class FavouriteViewModel(private val database: AppDatabase) : ViewModel(), ArticleClickHandler {

    private val loadEvent = emptyLiveData<Event<Unit>>()
    private var currentSource: LiveData<List<Article>> = emptyLiveData()

    val favArticles: LiveData<List<Article>> = loadEvent.switchMap {
        if (!it.handled) {
            currentSource = getFavouritesLiveData()
        }
        currentSource
    }

    val showBrowserEvent = MutableLiveData<Event<Article>>()

    fun clearFavourites() {
        viewModelScope.launch {
            database.articleDao().clearFavourite()
            loadFavourites()
        }
    }

    fun loadFavourites() = loadEvent.trigger()

    private fun getFavouritesLiveData() = liveData {
        val favArticles = database.articleDao().getFavourites()
        val articles = favArticles.map { it.article }
        emit(articles)
    }

    override fun onClick(article: Article) {
        showBrowserEvent.event(article)
    }
}