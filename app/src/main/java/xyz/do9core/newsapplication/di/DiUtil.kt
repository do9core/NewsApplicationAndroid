package xyz.do9core.newsapplication.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module

@Suppress("NOTHING_TO_INLINE")
inline fun KoinApplication.modules(vararg module: Module) =
    this.modules(module.toList())