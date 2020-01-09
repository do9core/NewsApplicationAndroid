package xyz.do9core.newsapplication.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<T : ViewDataBinding> : ContinuationActivity() {

    protected lateinit var binding: T
        private set

    @get:LayoutRes
    abstract val layoutResId: Int

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        setupBinding(binding)
        setupObservers()
    }

    protected open fun setupBinding(binding: T) = Unit
    protected open fun setupObservers() = Unit
}