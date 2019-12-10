package xyz.do9core.newsapplication.ui.article

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
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

    override fun setupBinding(binding: ActivityArticleBinding): Unit = with(binding) {
        binding.articleTitle = this@ArticleActivity.articleTitle
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if(articleUrl == EMPTY_URL) {
            return@with
        }

        val webView = WebViewFragment(articleUrl)
        supportFragmentManager.commit {
            add(webViewHost.id, webView)
        }
    }

    companion object {

        private const val EMPTY_URL = "app://blank"

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