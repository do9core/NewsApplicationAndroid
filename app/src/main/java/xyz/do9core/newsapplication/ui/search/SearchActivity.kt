package xyz.do9core.newsapplication.ui.search

import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.ActivitySearchBinding
import xyz.do9core.newsapplication.ui.article.ArticleActivity
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingActivity
import xyz.do9core.newsapplication.util.observe
import xyz.do9core.newsapplication.util.observeEvent

@BindLayout(R.layout.activity_search)
class SearchActivity : BindingActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy { ResultAdapter(viewModel) }

    override fun setupBinding(binding: ActivitySearchBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
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
        ArticleActivity.start(this, article.url, article.title)
    }
}
