package xyz.do9core.newsapplication.data

sealed class LoadResult<out T>(private val loadingState: LoadingState) {

    data class Error(val msg: String?) : LoadResult<Nothing>(LoadingState.FAILED)
    data class Success<out T>(val data: T) : LoadResult<T>(LoadingState.SUCCESS)
    object OK : LoadResult<Nothing>(LoadingState.SUCCESS)
    object Loading : LoadResult<Nothing>(LoadingState.LOADING)

    val isLoading get() = this.loadingState == LoadingState.LOADING
    val isSuccess get() = this.loadingState == LoadingState.SUCCESS
    val isError get() = this.loadingState == LoadingState.FAILED
}