package xyz.do9core.newsapplication.ui.article

import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentArticleBinding
import xyz.do9core.newsapplication.ui.article.webview.WebViewFragment
import xyz.do9core.newsapplication.ui.base.BindingFragment

class ArticleFragment : BindingFragment<FragmentArticleBinding>() {

    private val fragmentArgs by navArgs<ArticleFragmentArgs>()

    override val layoutResId: Int = R.layout.fragment_article

    override fun setupBinding(binding: FragmentArticleBinding): Unit = with(binding) {
        binding.articleTitle = fragmentArgs.articleTitle
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        if (fragmentArgs.articleUrl == "app://blank") {
            return@with
        }

        val webView = WebViewFragment(fragmentArgs.articleUrl)
        childFragmentManager.commit {
            add(webViewHost.id, webView)
        }
    }
}