package xyz.do9core.newsapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import xyz.do9core.liveeventbus.eventbus.LiveEventBus
import xyz.do9core.liveeventbus.eventbus.register
import xyz.do9core.liveeventbus.eventbus.subject
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.SnackbarEvent
import xyz.do9core.newsapplication.databinding.FragmentMainBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate

class MainFragment : BindingFragment<FragmentMainBinding>() {

    override fun createViewBinding(inflater: LayoutInflater): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LiveEventBus.subject<SnackbarEvent>(MainFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewPager.adapter = CategoryPagerAdapter(this@MainFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = Category.values()[position].title
            }.attach()
            setShowSearchListener { navigate(MainFragmentDirections.showSearch()) }
            navDrawer.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.app_menu_favourites -> MainFragmentDirections.showFavourites()
                    R.id.app_menu_app_info -> MainFragmentDirections.showAppInfo()
                    else -> null
                }?.also { dest -> navigate(dest) } != null
            }
        }

        LiveEventBus.register<SnackbarEvent>(viewLifecycleOwner, MainFragment) {
            when {
                it.text != null -> showSnackbar(it.text)
                it.textRes != null -> showSnackbar(it.textRes)
            }
        }
    }

    private fun showSnackbar(text: CharSequence) {
        Snackbar.make(binding.coordinator, text, Snackbar.LENGTH_LONG).show()
    }

    private fun showSnackbar(@StringRes text: Int) = showSnackbar(getString(text))

    companion object : LiveEventBus.Key
}