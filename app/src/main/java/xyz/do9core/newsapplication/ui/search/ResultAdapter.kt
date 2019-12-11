package xyz.do9core.newsapplication.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.ItemSearchResultBinding

class ResultAdapter(
    private val viewModel: SearchViewModel
) : PagedListAdapter<Article, ResultViewHolder>(Article.Differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder =
        ResultViewHolder.from(parent)
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item, viewModel)
    }
}

class ResultViewHolder(
    private val binding: ItemSearchResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Article, viewModel: SearchViewModel) {
        binding.model = model
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ResultViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSearchResultBinding.inflate(inflater, parent, false)
            return ResultViewHolder(binding)
        }
    }
}