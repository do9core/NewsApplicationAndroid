package xyz.do9core.newsapplication.ui.headline

import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.newsapplication.NavGraphDirections
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentHeadlineBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.ui.main.SharedViewModel
import xyz.do9core.newsapplication.util.event
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.viewObserve
import xyz.do9core.newsapplication.util.viewObserveEvent

class HeadlineFragment(private val category: Category) : BindingFragment<FragmentHeadlineBinding>() {

    private val sharedViewModel by sharedViewModel<SharedViewModel>()
    private val viewModel by viewModel<HeadlineViewModel> { parametersOf(category) }
    private val adapter by lazy { ArticleAdapter(viewModel) }

    override val layoutResId: Int = R.layout.fragment_headline

    override fun setupBinding(binding: FragmentHeadlineBinding) = with(binding) {
        viewModel = this@HeadlineFragment.viewModel
        articleList.layoutManager = LinearLayoutManager(requireContext())
        articleList.adapter = adapter
    }

    override fun setupObservers(): Unit = with(viewModel) {
        viewObserve(articles) { adapter.submitList(it) }
        viewObserve(networkState) { adapter.setLoadState(it) }
        viewObserveEvent(showArticleEvent) { showArticle(it) }
        viewObserveEvent(messageSnackbarEvent) { sharedViewModel.showErrorEvent.event(it) }
        viewObserveEvent(errorSnackbarEvent) {
            val msg = it.takeIf { it.isNotBlank() } ?: getString(R.string.app_save_favourite_failed)
            sharedViewModel.showSnackbarEvent.event(msg)
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