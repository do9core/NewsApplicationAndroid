package xyz.do9core.newsapplication.ui.article.webview

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentWebviewBinding
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingFragment

@BindLayout(R.layout.fragment_webview)
class WebViewFragment(
    private val targetUrl: String
) : BindingFragment<FragmentWebviewBinding>() {

    override fun setupBinding(binding: FragmentWebviewBinding) {
        super.setupBinding(binding)
        setupWebView(binding.webView)
    }

    private fun setupWebView(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if(newProgress < 100) {
                    binding.progressBar.progress = newProgress
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        webView.webViewClient = object : WebViewClient() {
            // TODO:
        }
    }

    override fun onStart() {
        super.onStart()
        binding.webView.loadUrl(targetUrl)
    }

    override fun onDestroy() {
        binding.webView.loadData("", null, null)
        binding.webView.destroy()
        super.onDestroy()
    }
}