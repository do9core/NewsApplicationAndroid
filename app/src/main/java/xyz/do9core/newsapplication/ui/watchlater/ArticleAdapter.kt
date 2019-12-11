package xyz.do9core.newsapplication.ui.watchlater

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.ui.common.ArticleViewHolder

class ArticleAdapter(
    private val viewModel: WatchLaterViewModel
) : ListAdapter<Article, ArticleViewHolder>(Article.Differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(
            data = getItem(position),
            itemClickHandler = viewModel,
            optionMenuButtonGone = true,
            favouriteHandler = null,
            watchLaterHandler = null
        )
}