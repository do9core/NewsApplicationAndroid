package xyz.do9core.newsapplication.ui.base

import androidx.annotation.LayoutRes

@Target(AnnotationTarget.CLASS)
annotation class BindLayout(@LayoutRes val layoutRes: Int)