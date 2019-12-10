package xyz.do9core.newsapplication.ui.watchlater

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.ActivityWatchLaterBinding
import xyz.do9core.newsapplication.ui.article.ArticleActivity
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingActivity
import xyz.do9core.newsapplication.util.observe
import xyz.do9core.newsapplication.util.observeEvent

@BindLayout(R.layout.activity_watch_later)
class WatchLaterActivity : BindingActivity<ActivityWatchLaterBinding>() {

    private val viewModel by viewModels<WatchLaterViewModel> {
        WatchLaterViewModel.Factory(application as NewsApplication)
    }
    private val adapter by lazy { ArticleAdapter(viewModel) }

    override fun setupBinding(binding: ActivityWatchLaterBinding) = with(binding) {
        watchLaterList.layoutManager = LinearLayoutManager(this@WatchLaterActivity)
        watchLaterList.adapter = adapter
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setupObservers() = with(viewModel) {
        observe(watchLaterArticles) { adapter.submitList(it) }
        observeEvent(showBrowserEvent) { showWatchLater(it) }
    }

    private fun showWatchLater(article: Article) {
        ArticleActivity.start(this, article.url, article.title)
    }
}