package xyz.do9core.newsapplication.ui.headline

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import xyz.do9core.newsapplication.NavGraphDirections
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentHeadlineBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.ui.main.MainFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.observe
import xyz.do9core.newsapplication.util.observeEvent

class HeadlineFragment(
    private val category: Category,
    private val parent: MainFragment
) : BindingFragment<FragmentHeadlineBinding>() {

    private val viewModel: HeadlineViewModel by viewModels {
        HeadlineViewModel.Factory(category, requireActivity().application as NewsApplication)
    }
    private val adapter by lazy { ArticleAdapter(viewModel) }

    override val layoutResId: Int = R.layout.fragment_headline

    override fun setupBinding(binding: FragmentHeadlineBinding) = with(binding) {
        viewModel = this@HeadlineFragment.viewModel
        articleList.layoutManager = LinearLayoutManager(requireContext())
        articleList.adapter = adapter
    }

    override fun setupObservers() = with(viewModel) {
        viewLifecycleOwner.observe(articles) { adapter.submitList(it) }
        viewLifecycleOwner.observe(networkState) { adapter.setLoadState(it) }
        viewLifecycleOwner.observeEvent(showArticleEvent) { showArticle(it) }
        viewLifecycleOwner.observeEvent(messageSnackbarEvent) {
            parent.showSnackbar(it, Snackbar.LENGTH_SHORT)
        }
        viewLifecycleOwner.observeEvent(errorSnackbarEvent) {
            val msg = it.takeIf { it.isNotBlank() } ?: getString(R.string.app_save_favourite_failed)
            parent.showSnackbar(msg, Snackbar.LENGTH_LONG)
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