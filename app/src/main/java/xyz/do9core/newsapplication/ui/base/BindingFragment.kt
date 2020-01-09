package xyz.do9core.newsapplication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingFragment<T : ViewDataBinding> : ContinuationFragment() {

    protected lateinit var binding: T
        private set

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = createBinding(inflater, container)
        .also { binding = it }
        .also { setupBinding(it) }
        .root

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setupObservers()
    }

    private fun createBinding(inflater: LayoutInflater, container: ViewGroup?): T =
        DataBindingUtil.inflate(inflater, layoutResId, container, false)

    @get:LayoutRes
    abstract val layoutResId: Int

    protected open fun setupBinding(binding: T) = Unit
    protected open fun setupObservers() = Unit
    protected open fun initData() = Unit
}