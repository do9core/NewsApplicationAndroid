package xyz.do9core.newsapplication.ui.main

import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentMainBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment

class MainFragment : BindingFragment<FragmentMainBinding>() {

    fun showSnackbar(text: CharSequence, time: Int) = with(binding) {
        Snackbar.make(coordinator, text, time).show()
    }

    fun showSnackbar(@StringRes text: Int, time: Int) = with(binding) {
        Snackbar.make(coordinator, text, time).show()
    }

    override val layoutResId: Int = R.layout.fragment_main

    override fun setupBinding(binding: FragmentMainBinding) = with(binding) {
        setShowSearchListener {
            MainFragmentDirections.showSearch().let { findNavController().navigate(it) }
        }
        viewPager.adapter = CategoryPagerAdapter(this@MainFragment)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Category.values()[position].title
        }.attach()

        navDrawer.setNavigationItemSelectedListener {
            val dest = when (it.itemId) {
                R.id.app_menu_favourites -> {
                    MainFragmentDirections.showFavourites()
                }
                R.id.app_menu_watch_later -> {
                    MainFragmentDirections.showWatchLater()
                }
                R.id.app_menu_app_info -> {
                    MainFragmentDirections.showAppInfo()
                }
                else -> null
            }
            if (dest != null) {
                findNavController().navigate(dest)
                true
            } else {
                false
            }
        }
    }
}