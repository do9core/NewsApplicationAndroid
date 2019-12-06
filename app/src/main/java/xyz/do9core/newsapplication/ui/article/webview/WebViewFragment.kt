package xyz.do9core.newsapplication.ui.article.webview

import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentWebviewBinding
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingFragment

@BindLayout(R.layout.fragment_webview)
class WebViewFragment(
    private val targetUrl: String
) : BindingFragment<FragmentWebviewBinding>()