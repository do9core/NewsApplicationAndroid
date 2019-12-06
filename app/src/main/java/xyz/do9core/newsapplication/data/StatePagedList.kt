package xyz.do9core.newsapplication.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class StatePagedList<T>(
    val livePagedList: LiveData<PagedList<T>>,
    val liveLoadResult: LiveData<LoadResult<PagedList<T>>>
)