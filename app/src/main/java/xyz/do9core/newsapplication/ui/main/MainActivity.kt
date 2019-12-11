package xyz.do9core.newsapplication.ui.main

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.ActivityMainBinding
import xyz.do9core.newsapplication.ui.appinfo.AppInfoActivity
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingActivity
import xyz.do9core.newsapplication.ui.favourite.FavouriteActivity
import xyz.do9core.newsapplication.ui.search.SearchActivity
import xyz.do9core.newsapplication.ui.watchlater.WatchLaterActivity
import xyz.do9core.newsapplication.util.start

@BindLayout(R.layout.activity_main)
class MainActivity : BindingActivity<ActivityMainBinding>() {

    fun showSnackbar(text: CharSequence, time: Int) = with(binding) {
        Snackbar.make(coordinator, text, time).show()
    }

    fun showSnackbar(@StringRes text: Int, time: Int) = with(binding) {
        Snackbar.make(coordinator, text, time).show()
    }

    override fun setupBinding(binding: ActivityMainBinding) = with(binding) {
        setShowSearchListener { start<SearchActivity>() }
        viewPager.adapter = CategoryPagerAdapter(this@MainActivity)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Category.values()[position].title
        }.attach()

        navDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.app_menu_favourites -> {
                    start<FavouriteActivity>()
                    true
                }
                R.id.app_menu_watch_later -> {
                    start<WatchLaterActivity>()
                    true
                }
                R.id.app_menu_app_info -> {
                    start<AppInfoActivity>()
                    true
                }
                else -> false
            }
        }
    }
}
