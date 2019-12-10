package xyz.do9core.newsapplication.ui.headline

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import xyz.do9core.newsapplication.ui.main.MainActivity
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentHeadlineBinding
import xyz.do9core.newsapplication.ui.article.ArticleActivity
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.util.observe
import xyz.do9core.newsapplication.util.observeEvent

@BindLayout(R.layout.fragment_headline)
class HeadlineFragment(
    private val category: Category
) : BindingFragment<FragmentHeadlineBinding>() {

    private val viewModel: HeadlineViewModel by viewModels {
        HeadlineViewModel.Factory(category, requireActivity().application as NewsApplication)
    }
    private val adapter by lazy { ArticleAdapter(viewModel) }

    override fun setupBinding(binding: FragmentHeadlineBinding) = with(binding) {
        viewModel= this@HeadlineFragment.viewModel
        articleList.layoutManager = LinearLayoutManager(requireContext())
        articleList.adapter = adapter
    }

    override fun setupObservers() = with(viewModel) {
        viewLifecycleOwner.observe(articles) { adapter.submitList(it) }
        viewLifecycleOwner.observe(networkState) { adapter.setLoadState(it) }
        viewLifecycleOwner.observeEvent(showArticleEvent) { showArticle(it) }
        viewLifecycleOwner.observeEvent(messageSnackbarEvent) {
            (requireActivity() as MainActivity).showSnackbar(it, Snackbar.LENGTH_SHORT)
        }
        viewLifecycleOwner.observeEvent(errorSnackbarEvent) {
            val msg = it ?: getString(R.string.app_save_favourite_failed)
            (requireActivity() as MainActivity).showSnackbar(msg, Snackbar.LENGTH_LONG)
        }
    }

    override fun initData() {
        viewModel.loadArticles()
    }

    private fun showArticle(article: Article) {
        ArticleActivity.start(requireContext(), article.url, article.title)
    }
}