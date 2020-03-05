package xyz.do9core.newsapplication.ui.appinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentAppInfoBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigateUp

class AppInfoFragment : BindingFragment<FragmentAppInfoBinding>() {

    override fun createViewBinding(inflater: LayoutInflater): FragmentAppInfoBinding =
        FragmentAppInfoBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navigateUp() }
        binding.newsApiLink.text = buildString {
            val label = getString(R.string.app_info_special_thanks)
            val newsApiUrl = getString(R.string.app_info_news_api_link)
            append("$label $newsApiUrl")
        }
    }
}