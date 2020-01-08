package xyz.do9core.newsapplication.ui.search

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.FragmentSearchBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.observe
import xyz.do9core.newsapplication.util.observeEvent

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy { ResultAdapter(viewModel) }

    override val layoutResId: Int = R.layout.fragment_search

    override fun setupBinding(binding: FragmentSearchBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.queryText.setOnEditorActionListener listener@{ _, event, _ ->
            return@listener if (event != EditorInfo.IME_ACTION_SEARCH) {
                false
            } else {
                viewModel.executeQuery()
                true
            }
        }
        binding.queryText.requestFocus()
    }

    override fun setupObservers() = with(viewModel) {
        observe(searchResult) { adapter.submitList(it) }
        observeEvent(showArticleEvent) { openArticle(it) }
    }

    private fun openArticle(article: Article) {
        SearchFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { findNavController().navigate(it) }
    }
}
