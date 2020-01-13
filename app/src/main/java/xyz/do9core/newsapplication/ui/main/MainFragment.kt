package xyz.do9core.newsapplication.ui.main

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentMainBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.viewObserveEvent

class MainFragment : BindingFragment<FragmentMainBinding>() {

    private val sharedViewModel by sharedViewModel<SharedViewModel>()

    override val layoutResId: Int = R.layout.fragment_main

    override fun setupBinding(binding: FragmentMainBinding) = with(binding) {
        setShowSearchListener { navigate(MainFragmentDirections.showSearch()) }
        viewPager.adapter = CategoryPagerAdapter(this@MainFragment)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Category.values()[position].title
        }.attach()

        navDrawer.setNavigationItemSelectedListener {
            val dest = when (it.itemId) {
                R.id.app_menu_favourites -> MainFragmentDirections.showFavourites()
                R.id.app_menu_watch_later -> MainFragmentDirections.showWatchLater()
                R.id.app_menu_app_info -> MainFragmentDirections.showAppInfo()
                else -> null
            }
            if (dest != null) {
                navigate(dest)
                true
            } else {
                false
            }
        }
    }

    override fun setupObservers(): Unit = with(sharedViewModel) {
        viewObserveEvent(showSnackbarEvent) { showSnackbar(it) }
        viewObserveEvent(showErrorEvent) { showSnackbar(it) }
    }

    private fun showSnackbar(text: CharSequence) = with(binding) {
        Snackbar.make(coordinator, text, Snackbar.LENGTH_LONG).show()
    }

    private fun showSnackbar(@StringRes text: Int) = with(binding) {
        Snackbar.make(coordinator, text, Snackbar.LENGTH_LONG).show()
    }
}