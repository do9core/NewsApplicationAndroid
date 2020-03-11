package xyz.do9core.newsapplication.ui.favourite

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.extensions.lifecycle.EventLiveData
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickedListener

class FavouriteViewModel(private val database: AppDatabase) : ViewModel() {

    private val loadEvent = MutableLiveData<Unit>()
    private var currentSource: LiveData<List<Article>>? = null

    val favArticles: LiveData<List<Article>> = loadEvent.switchMap {
        getFavouritesLiveData().also { currentSource = it }
    }

    val showBrowserEvent = EventLiveData<Article>()
    
    val articleClicked: ArticleClickedListener = { showBrowserEvent.call(it) }

    fun clearFavourites() {
        viewModelScope.launch {
            database.articleDao().clearFavourite()
            loadFavourites()
        }
    }

    fun loadFavourites() {
        loadEvent.value = Unit
    }

    private fun getFavouritesLiveData() = liveData {
        val favArticles = database.articleDao().getFavourites()
        val articles = favArticles.map { it.article }
        emit(articles)
    }
}