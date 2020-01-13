package xyz.do9core.newsapplication.di

import org.koin.core.KoinApplication
import org.koin.core.module.Module

fun KoinApplication.modules(vararg module: Module) =
    this.modules(module.toList())