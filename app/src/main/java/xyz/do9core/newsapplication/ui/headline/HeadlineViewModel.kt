package xyz.do9core.newsapplication.ui.headline

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.*
import androidx.paging.toLiveData
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.do9core.extensions.lifecycle.EventLiveData
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.extensions.storage.canWriteExternalStorage
import xyz.do9core.liveeventbus.eventbus.LiveEventBus
import xyz.do9core.liveeventbus.eventbus.get
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.db.AppDatabase
import xyz.do9core.newsapplication.data.model.*
import xyz.do9core.newsapplication.ui.common.ArticleClickedListener
import xyz.do9core.newsapplication.ui.main.MainFragment
import java.net.URL
import java.util.*

class HeadlineViewModel(
    category: Category,
    private val appContext: Context,
    private val database: AppDatabase
) : ViewModel() {

    private val workManager = WorkManager.getInstance(appContext)

    private val sourceFactory =
        HeadlineSourceFactory(viewModelScope, category, Country.UnitedStates)
    private val loadTrigger = MutableLiveData<Boolean>()

    val articleClicked: ArticleClickedListener = { showArticleEvent.call(it) }
    val favouriteHandler: ArticleClickedListener = ::saveToFavourite
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

    @JvmOverloads
    fun loadArticles(forceReload: Boolean = false) = loadTrigger.postValue(forceReload)

    private fun saveToFavourite(article: Article) {
        viewModelScope.launch {
            try {
                database.articleDao.saveFavouriteArticle(article)
                LiveEventBus.get<SnackbarEvent>(MainFragment).postNow(
                    resSnackEvent(R.string.app_save_favourite_success)
                )
            } catch (e: Exception) {
                LiveEventBus.get<SnackbarEvent>(MainFragment).postNow(
                    textSnackEvent(e.message.orEmpty())
                )
            }
        }
    }

    private fun saveImage(article: Article) {
//        val workData = workDataOf(SaveImageWorker.KEY_IMAGE_URL to article.urlToImage)
//        val workRequest = OneTimeWorkRequestBuilder<SaveImageWorker>()
//            .setInputData(workData)
//            .build()
//        workManager.enqueue(workRequest)
        if (!canWriteExternalStorage()) {
            LiveEventBus.get<SnackbarEvent>().postNow(
                textSnackEvent("Application cannot write external storage.")
            )
            return
        }
        val imageUrl = article.urlToImage
        if (imageUrl == null) {
            LiveEventBus.get<SnackbarEvent>().postNow(
                textSnackEvent("Nothing to save.")
            )
            return
        }
        viewModelScope.launch {
            try {
                val resolver = appContext.contentResolver
                val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                val fileName = UUID.randomUUID().toString().replace("-", "")
                val imageDetail = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                }
                val newFile = resolver.insert(collection, imageDetail)
                if (newFile == null) {
                    LiveEventBus.get<SnackbarEvent>().postNow(
                        textSnackEvent("Media info insert failed.")
                    )
                    return@launch
                }
                withContext(Dispatchers.IO) {
                    resolver.openOutputStream(newFile)!!.use { output ->
                        URL(imageUrl).openStream()!!.use { input ->
                            input.copyTo(output)
                        }
                    }
                }
                LiveEventBus.get<SnackbarEvent>().postNow(
                    resSnackEvent(R.string.app_headline_image_saved_message)
                )
            } catch (e: Exception) {
                LiveEventBus.get<SnackbarEvent>().postNow(
                    textSnackEvent(e.message.orEmpty())
                )
            }
        }
    }
}