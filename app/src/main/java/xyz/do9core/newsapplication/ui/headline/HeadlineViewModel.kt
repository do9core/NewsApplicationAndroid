package xyz.do9core.newsapplication.ui.headline

import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.snakydesign.livedataextensions.emptyLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.do9core.extensions.lifecycle.Event
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.extensions.storage.MediaImage
import xyz.do9core.extensions.storage.useLocalStorage
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.ui.common.ArticleClickedListener
import java.io.File
import java.net.URL
import java.util.*

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
    val showArticleEvent = emptyLiveData<Event<Article>>()
    val messageSnackbarEvent = emptyLiveData<Event<Int>>()
    val errorSnackbarEvent = emptyLiveData<Event<String>>()
    val imageSavedEvent = emptyLiveData<Event<Int>>()

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
        viewModelScope.launch {
            val path = appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            if (path == null) {
                // TODO: update error message
                errorSnackbarEvent.call("")
                return@launch
            }
            val fileName = UUID.randomUUID().toString()
            val newFile = File(path, fileName)
            val success = withContext(Dispatchers.IO) {
                if (!newFile.createNewFile()) {
                    return@withContext false
                }
                try {
                    URL(article.urlToImage).openStream().use { inputStream ->
                        newFile.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    return@withContext true
                } catch (e: Exception) {
                    return@withContext false
                }
            }

            if (!success) {
                // TODO: update error message
                errorSnackbarEvent.call("")
                return@launch
            }

            // TODO: update image info model
            val image = MediaImage(
                mimeType = "image/jpeg",
                imageUri = newFile.toUri()
            )

            appContext.useLocalStorage {
                saveImage(image)
            }

            // TODO: update notification text resource
            imageSavedEvent.call(0)
        }
    }
}