package xyz.do9core.newsapplication.ui.main

import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import xyz.do9core.newsapplication.databinding.ActivityMainBinding
import xyz.do9core.newsapplication.di.LayoutIdName
import xyz.do9core.newsapplication.ui.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override val layoutResId: Int by inject(named(LayoutIdName.Main))
}
