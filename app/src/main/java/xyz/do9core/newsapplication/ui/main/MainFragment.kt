package xyz.do9core.newsapplication.ui.main

import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import splitties.snackbar.snack
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentMainBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate

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
            when (it.itemId) {
                R.id.app_menu_favourites -> MainFragmentDirections.showFavourites()
                R.id.app_menu_watch_later -> MainFragmentDirections.showWatchLater()
                R.id.app_menu_app_info -> MainFragmentDirections.showAppInfo()
                else -> null
            }?.also { dest -> navigate(dest) } != null
        }
    }

    override fun setupObservers(): Unit = with(sharedViewModel) {
        viewObserveEvent(showSnackbarEvent) { showSnackbar(it) }
        viewObserveEvent(showErrorEvent) { showSnackbar(it) }
    }

    private fun showSnackbar(text: CharSequence) = binding.coordinator.snack(text).show()

    private fun showSnackbar(@StringRes text: Int) = binding.coordinator.snack(text).show()
}