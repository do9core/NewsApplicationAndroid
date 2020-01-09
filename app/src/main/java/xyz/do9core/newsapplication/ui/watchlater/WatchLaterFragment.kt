package xyz.do9core.newsapplication.ui.watchlater

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.FragmentWatchLaterBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.navigateUp
import xyz.do9core.newsapplication.util.observe
import xyz.do9core.newsapplication.util.observeEvent

class WatchLaterFragment : BindingFragment<FragmentWatchLaterBinding>() {

    private val viewModel by viewModels<WatchLaterViewModel> { WatchLaterViewModel.Factory() }
    private val adapter by lazy { ArticleAdapter(viewModel) }

    override val layoutResId: Int = R.layout.fragment_watch_later

    override fun setupBinding(binding: FragmentWatchLaterBinding) = with(binding) {
        watchLaterList.layoutManager = LinearLayoutManager(requireContext())
        watchLaterList.adapter = adapter
        toolbar.setNavigationOnClickListener { navigateUp() }
    }

    override fun setupObservers() = with(viewModel) {
        observe(watchLaterArticles) { adapter.submitList(it) }
        observeEvent(showBrowserEvent) { showWatchLater(it) }
    }

    private fun showWatchLater(article: Article) {
        WatchLaterFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}