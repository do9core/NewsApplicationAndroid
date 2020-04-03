package xyz.do9core.newsapplication.ui.search

import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.snakydesign.livedataextensions.combineLatest
import com.snakydesign.livedataextensions.startWith
import xyz.do9core.extensions.lifecycle.EventLiveData
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.datasource.HeadlineSourceFactory
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country

class SearchViewModel(savedState: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_PREFIX = "SearchViewModel"

        const val KEY_QUERY_TEXT = "$KEY_PREFIX.QUERY_TEXT"
        const val KEY_FRONT_LAYER_EXPANDED = "$KEY_PREFIX.FRONT_LAYER_EXPANDED"
        const val KEY_COUNTRY_INDEX = "$KEY_PREFIX.COUNTRY_INDEX"
        const val KEY_CATEGORY_INDEX = "$KEY_PREFIX.CATEGORY_INDEX"
    }

    private val dataFactory = MutableLiveData<HeadlineSourceFactory>()
    private val dataSource = dataFactory.switchMap { it.dataSource }
    private val networkState = dataSource.switchMap { it.networkState }
    val searchResult = dataFactory.switchMap { it.toLiveData(10) }
    val isLoading = networkState.map { it.isLoading }.startWith(false)

    val queryText = savedState.getLiveData<String>(KEY_QUERY_TEXT)
    val showArticleEvent = EventLiveData<Article>()

    val frontLayerExpanded = savedState.getLiveData(KEY_FRONT_LAYER_EXPANDED, false)
    val navigationIcon = frontLayerExpanded.map { expanded ->
        if (expanded) R.drawable.ic_close else R.drawable.ic_menu
    }

    val showNoResult = combineLatest(isLoading, searchResult, frontLayerExpanded) {
            loading, result, expanded ->
        when {
            expanded.not() -> false
            loading or result.isNullOrEmpty() -> true
            else -> false
        }
    }.startWith(false)

    val countryIndex = savedState.getLiveData(KEY_COUNTRY_INDEX, -1)
    val country: LiveData<Country?> = countryIndex.map { index ->
        Country.values().getOrNull(index)
    }

    val categoryIndex = savedState.getLiveData(KEY_CATEGORY_INDEX, -1)
    val category: LiveData<Category?> = categoryIndex.map { index ->
        Category.values().getOrNull(index)
    }

    private val onChangedObserver = Observer<Unit> { executeQuery() }

    private val changed = object : MediatorLiveData<Unit>() {
        init {
            addSource(queryText) { postValue(Unit) }
            addSource(countryIndex) { postValue(Unit) }
            addSource(categoryIndex) { postValue(Unit) }
            observeForever(onChangedObserver)
        }
    }

    fun executeQuery() {
        val sourceFactory = HeadlineSourceFactory(
            coroutineScope = viewModelScope,
            category = category.value,
            country = country.value,
            query = queryText.value.orEmpty()
        )
        dataFactory.value = sourceFactory
    }

    fun showArticle(article: Article) = showArticleEvent.call(article)

    override fun onCleared() {
        changed.removeObserver(onChangedObserver)
    }
}