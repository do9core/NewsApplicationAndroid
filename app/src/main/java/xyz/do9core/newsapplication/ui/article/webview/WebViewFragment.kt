package xyz.do9core.newsapplication.ui.article.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import xyz.do9core.newsapplication.databinding.FragmentWebviewBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment

class WebViewFragment(private val targetUrl: String) : BindingFragment<FragmentWebviewBinding>() {

    private val refreshing = ObservableBoolean(false)

    private fun WebView.setup() {
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress < 100) {
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

    override fun createViewBinding(inflater: LayoutInflater): FragmentWebviewBinding =
        FragmentWebviewBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.refreshingState = refreshing
        binding.webView.setup()
        binding.setRefreshWebView {
            binding.webView.reload()
        }
    }

    override fun onDestroyView() {
        binding.webView.loadData("", null, null)
        binding.webView.destroy()
        // After super.onDestroyView() the binding is not available
        super.onDestroyView()
    }

    class Factory(private val url: String) : FragmentFactory() {

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            if (className == WebViewFragment::class.java.name) {
                return WebViewFragment(targetUrl = url)
            }
            return super.instantiate(classLoader, className)
        }
    }
}