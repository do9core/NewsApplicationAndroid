package xyz.do9core.newsapplication.ui.watchlater

import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.get
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import xyz.do9core.extensions.lifecycle.observe
import xyz.do9core.extensions.lifecycle.observeEvent
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.FragmentWatchLaterBinding
import xyz.do9core.newsapplication.di.LayoutIdName
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.navigateUp

class WatchLaterFragment : BindingFragment<FragmentWatchLaterBinding>() {

    private val viewModel by viewModel<WatchLaterViewModel>()
    private val adapter: ArticleAdapter by currentScope.inject { parametersOf(viewModel) }

    override val layoutResId: Int = get(named(LayoutIdName.WatchLater))

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