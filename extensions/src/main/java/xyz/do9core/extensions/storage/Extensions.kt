package xyz.do9core.extensions.storage

import android.content.Context

inline fun Context.useLocalStorage(block: LocalStorage.() -> Unit) =
    LocalStorage(this).use(block)