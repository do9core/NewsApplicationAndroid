package xyz.do9core.newsapplication.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.ItemArticleBinding

class ArticleViewHolder private constructor(
    private val binding: ItemArticleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: ArticleViewModel) {
        binding.viewModel = viewModel
        binding.setShowDropDownHandler { dropDownButton ->
            val context = binding.root.context
            val popup = PopupMenu(context, dropDownButton)
            popup.menuInflater.inflate(R.menu.article_pop_up_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.article_pop_up_favourite -> {
                        viewModel.favouriteClicked?.invoke(viewModel.data)
                        true
                    }
                    R.id.article_pop_up_watch_later -> {
                        viewModel.watchLaterClicked?.invoke(viewModel.data)
                        true
                    }
                    R.id.article_pop_up_save_image -> {
                        viewModel.saveImageClicked?.invoke(viewModel.data)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemArticleBinding.inflate(inflater, parent, false)
            return ArticleViewHolder(binding)
        }
    }
}