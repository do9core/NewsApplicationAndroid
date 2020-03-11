package xyz.do9core.newsapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import xyz.do9core.extensions.lifecycle.observe
import xyz.do9core.liveeventbus.bus.LiveEventBus
import xyz.do9core.liveeventbus.bus.subject
import xyz.do9core.liveeventbus.bus.with
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentMainBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate

class MainFragment : BindingFragment<FragmentMainBinding>() {

    override fun createViewBinding(inflater: LayoutInflater): FragmentMainBinding
        = FragmentMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LiveEventBus.Default.subject<String>(MainFragment)
        LiveEventBus.Default.subject<Int>(MainFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewPager.adapter = CategoryPagerAdapter(this@MainFragment)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = Category.values()[position].title
        }.attach()

        binding.setShowSearchListener { navigate(MainFragmentDirections.showSearch()) }
        binding.navDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.app_menu_favourites -> MainFragmentDirections.showFavourites()
                R.id.app_menu_watch_later -> MainFragmentDirections.showWatchLater()
                R.id.app_menu_app_info -> MainFragmentDirections.showAppInfo()
                else -> null
            }?.also { dest -> navigate(dest) } != null
        }

        with(viewLifecycleOwner) {
            observe(LiveEventBus.Default.with<String>(MainFragment), ::showSnackbar)
            observe(LiveEventBus.Default.with<Int>(MainFragment), ::showSnackbar)
        }
    }

    private fun showSnackbar(text: CharSequence) {
        Snackbar.make(binding.coordinator, text, Snackbar.LENGTH_LONG).show()
    }

    private fun showSnackbar(@StringRes text: Int) = showSnackbar(getString(text))

    companion object : LiveEventBus.Key
}