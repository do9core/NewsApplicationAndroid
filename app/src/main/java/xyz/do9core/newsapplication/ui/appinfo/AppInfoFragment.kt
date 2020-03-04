package xyz.do9core.newsapplication.ui.appinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentAppInfoBinding
import xyz.do9core.newsapplication.ui.base.ContinuationFragment
import xyz.do9core.newsapplication.util.navigateUp

class AppInfoFragment : ContinuationFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAppInfoBinding.inflate(inflater)
        binding.toolbar.setNavigationOnClickListener { navigateUp() }
        binding.newsApiLink.text = buildString {
            val label = getString(R.string.app_info_special_thanks)
            val newsApiUrl = getString(R.string.app_info_news_api_link)
            append("$label $newsApiUrl")
        }
        return binding.root
    }
}