package xyz.do9core.newsapplication.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.ui.headline.HeadlineFragment

class CategoryPagerAdapter(fragment: MainFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = Category.values().size
    override fun createFragment(position: Int): Fragment =
        HeadlineFragment(Category.values()[position])
}