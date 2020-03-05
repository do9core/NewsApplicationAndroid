package xyz.do9core.newsapplication.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.commit
import androidx.navigation.fragment.navArgs
import xyz.do9core.newsapplication.databinding.FragmentArticleBinding
import xyz.do9core.newsapplication.ui.article.webview.WebViewFragment
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigateUp

class ArticleFragment : BindingFragment<FragmentArticleBinding>() {

    private val navArgs by navArgs<ArticleFragmentArgs>()

    override fun createViewBinding(inflater: LayoutInflater): FragmentArticleBinding =
        FragmentArticleBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = navArgs.articleTitle
        binding.toolbar.setNavigationOnClickListener { navigateUp() }

        if (navArgs.articleUrl != "app://blank") {
            val webView = WebViewFragment(navArgs.articleUrl)
            childFragmentManager.fragmentFactory = WebViewFragment.Factory(navArgs.articleUrl)
            childFragmentManager.commit {
                replace(binding.webViewHost.id, webView)
            }
        }
    }
}