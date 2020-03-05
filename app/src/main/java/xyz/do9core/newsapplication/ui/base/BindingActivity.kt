package xyz.do9core.newsapplication.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<T : ViewBinding> : ContinuationActivity() {

    protected lateinit var binding: T
        private set

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
    }

    abstract fun createBinding(): T
}