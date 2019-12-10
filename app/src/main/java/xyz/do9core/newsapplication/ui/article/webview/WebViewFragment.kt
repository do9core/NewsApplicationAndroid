package xyz.do9core.newsapplication.ui.article.webview

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.databinding.ObservableBoolean
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentWebviewBinding
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingFragment

@BindLayout(R.layout.fragment_webview)
class WebViewFragment(
    private val targetUrl: String
) : BindingFragment<FragmentWebviewBinding>() {

    private val refreshing = ObservableBoolean(false)

    override fun setupBinding(binding: FragmentWebviewBinding) {
        super.setupBinding(binding)
        binding.webView.setup()
        binding.refreshingState = refreshing
        binding.setRefreshWebView {
            binding.webView.reload()
        }
    }

    private fun WebView.setup() {
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if(newProgress < 100) {
                    refreshing.set(true)
                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.progress = newProgress
                } else {
                    refreshing.set(false)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        loadUrl(targetUrl)
    }

    override fun onDestroy() {
        binding.webView.loadData("", null, null)
        binding.webView.destroy()
        super.onDestroy()
    }
}