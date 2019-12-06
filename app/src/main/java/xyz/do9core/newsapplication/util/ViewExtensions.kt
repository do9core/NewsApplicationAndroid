package xyz.do9core.newsapplication.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(length: Int = Toast.LENGTH_SHORT, text: CharSequence) {
    Toast.makeText(this, text, length).show()
}

fun Context.toast(length: Int = Toast.LENGTH_SHORT, textBuilder: () -> CharSequence) =
    toast(length, textBuilder.invoke())

fun Fragment.toast(length: Int = Toast.LENGTH_SHORT, text: CharSequence) =
    requireContext().toast(length, text)

fun Fragment.toast(length: Int = Toast.LENGTH_SHORT, textBuilder: () -> CharSequence) =
    requireContext().toast(length, textBuilder)
