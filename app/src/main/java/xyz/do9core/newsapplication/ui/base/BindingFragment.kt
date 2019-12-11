package xyz.do9core.newsapplication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BindingFragment<T : ViewDataBinding> : Fragment() {

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
        DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)

    @LayoutRes
    private fun getLayoutResId(): Int {
        val bindingAnnotation = this::class.annotations.find { it is BindLayout } as? BindLayout
        return bindingAnnotation?.layoutRes ?: 0
    }

    protected open fun setupBinding(binding: T) = Unit
    protected open fun setupObservers() = Unit
    protected open fun initData() = Unit
}