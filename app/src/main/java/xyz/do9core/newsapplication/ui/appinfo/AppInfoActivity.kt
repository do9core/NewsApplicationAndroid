package xyz.do9core.newsapplication.ui.appinfo

import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.ActivityAppInfoBinding
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingActivity


@BindLayout(R.layout.activity_app_info)
class AppInfoActivity : BindingActivity<ActivityAppInfoBinding>() {

    override fun setupBinding(binding: ActivityAppInfoBinding) {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.newsApiLink.text = buildString {
            val label = getString(R.string.app_info_special_thanks)
            val newsApiUrl = getString(R.string.app_info_news_api_link)
            append("$label $newsApiUrl")
        }
    }
}