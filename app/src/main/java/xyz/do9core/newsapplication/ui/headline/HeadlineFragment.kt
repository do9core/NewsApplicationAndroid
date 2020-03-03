package xyz.do9core.newsapplication.ui.headline

import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.get
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import xyz.do9core.extensions.fragment.viewObserve
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.extensions.lifecycle.call
import xyz.do9core.newsapplication.NavGraphDirections
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentHeadlineBinding
import xyz.do9core.newsapplication.di.LayoutIdName
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.ui.main.SharedViewModel
import xyz.do9core.newsapplication.util.navigate

class HeadlineFragment(private val category: Category) :
    BindingFragment<FragmentHeadlineBinding>() {

    private val sharedViewModel by sharedViewModel<SharedViewModel>()
    private val viewModel by viewModel<HeadlineViewModel> { parametersOf(category) }
    private val adapter: ArticleAdapter by currentScope.inject { parametersOf(viewModel) }

    override val layoutResId: Int = get(named(LayoutIdName.Headline))

    override fun setupBinding(binding: FragmentHeadlineBinding) = with(binding) {
        viewModel = this@HeadlineFragment.viewModel
        articleList.layoutManager = LinearLayoutManager(requireContext())
        articleList.adapter = adapter
    }

    override fun setupObservers(): Unit = with(viewModel) {
        viewObserve(articles) { adapter.submitList(it) }
        viewObserve(networkState) { adapter.setLoadState(it) }
        viewObserveEvent(showArticleEvent) { showArticle(it) }
        viewObserveEvent(messageSnackbarEvent) { sharedViewModel.showErrorEvent.call(it) }
        viewObserveEvent(errorSnackbarEvent) {
            val msg = it.takeIf { it.isNotBlank() } ?: getString(R.string.app_save_favourite_failed)
            sharedViewModel.showSnackbarEvent.call(msg)
        }
    }

    override fun initData() {
        viewModel.loadArticles()
    }

    private fun showArticle(article: Article) {
        NavGraphDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}