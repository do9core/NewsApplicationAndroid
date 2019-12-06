package xyz.do9core.newsapplication.ui.headline

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.do9core.newsapplication.data.LoadResult
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleViewHolder
import xyz.do9core.newsapplication.ui.common.LoadingViewHolder
import java.security.InvalidParameterException

class ArticleAdapter(
    private val viewModel: HeadlineViewModel
) : PagedListAdapter<Article, RecyclerView.ViewHolder>(Article.Differ) {

    private var loadResult: LoadResult<*> = LoadResult.OK

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE -> ArticleViewHolder.from(parent)
            LOADING_VIEW_TYPE -> LoadingViewHolder.from(parent)
            else -> throw InvalidParameterException("Unknown view type.")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            ITEM_VIEW_TYPE ->
                (holder as ArticleViewHolder).bind(
                    data = getItem(position),
                    itemClickHandler = viewModel.itemClickHandler,
                    optionMenuButtonGone = false,
                    favouriteHandler = viewModel.favouriteHandler,
                    watchLaterHandler = viewModel.watchLaterHandler
                )
            LOADING_VIEW_TYPE ->
                (holder as LoadingViewHolder).bind(
                    this.itemCount <= 1 || !loadResult.isLoading
                )
            else -> throw InvalidParameterException("Unknown view type.")
        }
    }

    fun setLoadState(state: LoadResult<*>?) {
        this.loadResult = state ?: return
        notifyItemChanged(this.itemCount - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return when(position + 1) {
            this.itemCount -> LOADING_VIEW_TYPE
            else -> ITEM_VIEW_TYPE
        }
    }

    override fun getItemCount() = super.getItemCount() + 1

    companion object {
        private const val LOADING_VIEW_TYPE = 1
        private const val ITEM_VIEW_TYPE = 0
    }
}
