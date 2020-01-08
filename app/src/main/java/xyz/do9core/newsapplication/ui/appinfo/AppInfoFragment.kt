package xyz.do9core.newsapplication.ui.appinfo

import androidx.navigation.fragment.findNavController
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.databinding.FragmentAppInfoBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment

class AppInfoFragment : BindingFragment<FragmentAppInfoBinding>() {

    override val layoutResId: Int = R.layout.fragment_app_info

    override fun setupBinding(binding: FragmentAppInfoBinding) {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.newsApiLink.text = buildString {
            val label = getString(R.string.app_info_special_thanks)
            val newsApiUrl = getString(R.string.app_info_news_api_link)
            append("$label $newsApiUrl")
        }
    }
}