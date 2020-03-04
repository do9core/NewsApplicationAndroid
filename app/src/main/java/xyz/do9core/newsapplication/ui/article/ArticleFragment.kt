package xyz.do9core.newsapplication.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.navArgs
import xyz.do9core.newsapplication.databinding.FragmentArticleBinding
import xyz.do9core.newsapplication.ui.article.webview.WebViewFragment
import xyz.do9core.newsapplication.ui.base.ContinuationFragment
import xyz.do9core.newsapplication.util.navigateUp

class ArticleFragment : ContinuationFragment() {

    private val navArgs by navArgs<ArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleBinding.inflate(inflater)
        setupBinding(binding)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setupBinding(binding: FragmentArticleBinding): Unit = with(binding) {
        binding.toolbar.title = navArgs.articleTitle
        binding.toolbar.setNavigationOnClickListener { navigateUp() }

        if (navArgs.articleUrl == "app://blank") {
            return@with
        }

        val webView = WebViewFragment(navArgs.articleUrl)
        childFragmentManager.commit {
            add(webViewHost.id, webView)
        }
    }
}