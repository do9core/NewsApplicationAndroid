package xyz.do9core.newsapplication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<T : ViewBinding> : ContinuationFragment() {

    private var _binding: T? = null

    // Only available between onCreateView() and onDestroyView()
    protected val binding: T get() = _binding!!

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = createViewBinding(inflater).also { _binding = it }.root

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun createViewBinding(inflater: LayoutInflater): T
}