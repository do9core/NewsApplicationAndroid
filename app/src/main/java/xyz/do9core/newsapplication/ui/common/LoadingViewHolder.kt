package xyz.do9core.newsapplication.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.do9core.newsapplication.databinding.ItemLoadingBinding

class LoadingViewHolder private constructor(
    private val binding: ItemLoadingBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hideProgress: Boolean) {
        binding.loadFinished = hideProgress
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LoadingViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLoadingBinding.inflate(inflater, parent, false)
            return LoadingViewHolder(binding)
        }
    }
}
