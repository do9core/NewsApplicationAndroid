package xyz.do9core.newsapplication.ui.article

import androidx.fragment.app.commit
import androidx.navigation.fragment.navArgs
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import xyz.do9core.newsapplication.databinding.FragmentArticleBinding
import xyz.do9core.newsapplication.di.LayoutIdName
import xyz.do9core.newsapplication.ui.article.webview.WebViewFragment
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigateUp

class ArticleFragment : BindingFragment<FragmentArticleBinding>() {

    private val fragmentArgs by navArgs<ArticleFragmentArgs>()

    override val layoutResId: Int = get(named(LayoutIdName.Article))

    override fun setupBinding(binding: FragmentArticleBinding): Unit = with(binding) {
        binding.articleTitle = fragmentArgs.articleTitle
        binding.toolbar.setNavigationOnClickListener { navigateUp() }

        if (fragmentArgs.articleUrl == "app://blank") {
            return@with
        }

        val webView = WebViewFragment(fragmentArgs.articleUrl)
        childFragmentManager.commit {
            add(webViewHost.id, webView)
        }
    }
}