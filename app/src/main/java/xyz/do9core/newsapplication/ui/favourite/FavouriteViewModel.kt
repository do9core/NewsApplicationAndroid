package xyz.do9core.newsapplication.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.snakydesign.livedataextensions.emptyLiveData
import com.snakydesign.livedataextensions.switchMap
import kotlinx.coroutines.launch
import xyz.do9core.extensions.lifecycle.EventLiveData
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleClickedListener

class FavouriteViewModel(private val database: AppDatabase) : ViewModel() {

    private val loadEvent = EventLiveData()
    private var currentSource: LiveData<List<Article>> = emptyLiveData()

    val favArticles: LiveData<List<Article>> = loadEvent.switchMap {
        if (!it.handled) {
            currentSource = getFavouritesLiveData()
        }
        currentSource
    }

    val showBrowserEvent = EventLiveData<Article>()
    val snackbarEvent = EventLiveData<Int>()
    val articleClicked: ArticleClickedListener = { showBrowserEvent.call(it) }

    fun clearFavourites() {
        viewModelScope.launch {
            database.articleDao.clearFavourite()
            loadFavourites()
            snackbarEvent.call(R.string.app_clear_fav_snack)
        }
    }

    fun loadFavourites() = loadEvent.call()

    fun removeArticle(article: Article) {
        viewModelScope.launch {
            database.articleDao.deleteFavourite(article.url)
            loadFavourites()
            snackbarEvent.call(R.string.app_remove_fav_snack)
        }
    }

    private fun getFavouritesLiveData() = liveData {
        val favArticles = database.articleDao.getFavourites()
        val articles = favArticles.map { it.article }
        emit(articles)
    }
}