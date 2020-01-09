package xyz.do9core.newsapplication.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.data.remote.RemoteDataSource

class HeadlineSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val category: Category,
    private val country: Country,
    private val query: String = "",
    var fromLocal: Boolean = false
) : DataSource.Factory<Int, Article>() {

    val dataSource = MutableLiveData<HeadlineRemoteSource>()

    override fun create(): DataSource<Int, Article> {
        if (fromLocal) {
            // TODO
        }
        return HeadlineRemoteSource(coroutineScope, category, country, query)
            .also { dataSource.postValue(it) }
    }
}

class HeadlineRemoteSource(
    private val coroutineScope: CoroutineScope,
    private val category: Category,
    private val country: Country,
    private val query: String
) : PageKeyedDataSource<Int, Article>() {

    private val _networkState = MutableLiveData<LoadResult<*>>()
    val networkState: LiveData<LoadResult<*>> = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        launchApi(params.requestedLoadSize + 1, 0) {
            val nextPageKey =
                if (it.size < params.requestedLoadSize) {
                    null
                } else {
                    1
                }
            callback.onResult(it, null, nextPageKey)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        launchApi(params.requestedLoadSize + 1, params.key) {
            val nextPageKey =
                if (it.size < params.requestedLoadSize) {
                    null
                } else {
                    params.key + 1
                }
            callback.onResult(it, nextPageKey)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        launchApi(params.requestedLoadSize + 1, params.key) {
            val lastPageKey =
                if (it.size < params.requestedLoadSize) {
                    null
                } else {
                    params.key - 1
                }
            callback.onResult(it, lastPageKey)
        }
    }

    private fun launchApi(pageSize: Int, page: Int, loadedCallback: (List<Article>) -> Unit) =
        coroutineScope.launch(Dispatchers.IO) {
            try {
                _networkState.postValue(LoadResult.Loading)
                val result = RemoteDataSource.fetchHeadline(
                    country = country.code,
                    category = category.title,
                    query = query,
                    pageSize = pageSize,
                    page = page
                ).articles
                loadedCallback.invoke(result)
                _networkState.postValue(LoadResult.OK)
            } catch (e: Exception) {
                _networkState.postValue(LoadResult.Error(e.message))
            }
        }
}