package xyz.do9core.newsapplication.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.fragment.viewObserve
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.FragmentSearchBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.navigateUp

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()
    private val adapter: ResultAdapter by lifecycleScope.inject { parametersOf(viewModel) }

    override fun createViewBinding(inflater: LayoutInflater): FragmentSearchBinding =
        FragmentSearchBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.toolbar.setNavigationOnClickListener { navigateUp() }
        binding.queryText.setOnEditorActionListener listener@{ _, event, _ ->
            return@listener if (event != EditorInfo.IME_ACTION_SEARCH) {
                false
            } else {
                viewModel.executeQuery()
                true
            }
        }

        with(viewModel) {
            viewObserve(searchResult) { adapter.submitList(it) }
            viewObserveEvent(showArticleEvent) { openArticle(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.queryText.requestFocus()
    }

    private fun openArticle(article: Article) {
        SearchFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}
