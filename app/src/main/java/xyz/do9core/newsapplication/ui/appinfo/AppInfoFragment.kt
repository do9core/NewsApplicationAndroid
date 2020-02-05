package xyz.do9core.newsapplication.ui.appinfo

import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentAppInfoBinding
import xyz.do9core.newsapplication.di.LayoutIdName
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigateUp

class AppInfoFragment : BindingFragment<FragmentAppInfoBinding>() {

    override val layoutResId: Int = get(named(LayoutIdName.AppInfo))

    override fun setupBinding(binding: FragmentAppInfoBinding) {
        binding.toolbar.setNavigationOnClickListener { navigateUp() }
        binding.newsApiLink.text = buildString {
            val label = getString(R.string.app_info_special_thanks)
            val newsApiUrl = getString(R.string.app_info_news_api_link)
            append("$label $newsApiUrl")
        }
    }
}