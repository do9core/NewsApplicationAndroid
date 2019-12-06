package xyz.do9core.newsapplication.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import java.io.Serializable

fun Fragment.navigate(direction: NavDirections) =
    findNavController().navigate(direction)

fun <T> AppCompatActivity.extra(key: String, defaultValue: T): Lazy<T> {
    return object : Lazy<T> {
        private var extra: T? = null
        override fun isInitialized(): Boolean = (extra != null)
        override val value: T
            get() {
                if(extra == null) {
                    @Suppress("UNCHECKED_CAST")
                    val t = intent.extras?.get(key) as? T
                    extra = t ?: defaultValue
                }
                return extra!!
            }
    }
}