package xyz.do9core.newsapplication.ui.article

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.ActivityArticleBinding
import xyz.do9core.newsapplication.ui.article.webview.WebViewFragment
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingActivity
import xyz.do9core.newsapplication.util.extra
import xyz.do9core.newsapplication.util.start

@BindLayout(R.layout.activity_article)
class ArticleActivity : BindingActivity<ActivityArticleBinding>() {

    private val articleUrl: String by extra(URL_KEY, EMPTY_URL)
    private val articleTitle: String by extra(TITLE_KEY, "")

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // TODO
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO
        return super.onOptionsItemSelected(item)
    }

    override fun setupBinding(binding: ActivityArticleBinding): Unit = with(binding) {
        binding.articleTitle = this@ArticleActivity.articleTitle
        setSupportActionBar(toolbar)
        if(articleUrl != EMPTY_URL) {
            return@with
        }

        val webView = WebViewFragment(articleUrl)
        supportFragmentManager
            .beginTransaction()
            .add(webViewHost.id, webView)
            .commit()
    }

    companion object {

        private const val EMPTY_URL = "app://empty"

        const val URL_KEY = "URL_KEY"
        const val TITLE_KEY = "TITLE_KEY"

        fun start(source: Context, url: String, title: String) {
            source.start<ArticleActivity> {
                bundleOf(
                    URL_KEY to url,
                    TITLE_KEY to title
                )
            }
        }
    }
}