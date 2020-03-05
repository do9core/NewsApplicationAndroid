package xyz.do9core.newsapplication.ui.watchlater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.lifecycle.observe
import xyz.do9core.extensions.lifecycle.observeEvent
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.FragmentWatchLaterBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.navigateUp

class WatchLaterFragment : BindingFragment<FragmentWatchLaterBinding>() {

    private val viewModel by viewModel<WatchLaterViewModel>()
    private val adapter: ArticleAdapter by currentScope.inject { parametersOf(viewModel) }

    override fun createViewBinding(inflater: LayoutInflater): FragmentWatchLaterBinding =
        FragmentWatchLaterBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.watchLaterList.adapter = adapter
        binding.toolbar.setNavigationOnClickListener { navigateUp() }
        with(viewModel) {
            observe(watchLaterArticles) { adapter.submitList(it) }
            observeEvent(showBrowserEvent) { showWatchLater(it) }
        }
    }

    private fun showWatchLater(article: Article) {
        WatchLaterFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}