package xyz.do9core.newsapplication.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T
        private set

    @LayoutRes
    private fun getLayoutResId(): Int {
        val bindAnnotation = this::class.annotations.find { it is BindLayout } as? BindLayout
        return bindAnnotation?.layoutRes ?: 0
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        setupBinding(binding)
        setupObservers()
    }

    protected open fun setupBinding(binding: T) = Unit
    protected open fun setupObservers() = Unit
}