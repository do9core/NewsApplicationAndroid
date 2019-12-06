package xyz.do9core.newsapplication.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T: AppCompatActivity> Context.start() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T: AppCompatActivity> Fragment.start() =
    requireContext().start<T>()

inline fun <reified T: AppCompatActivity> Context.start(paramProducer: () -> Bundle) {
    val bundle = paramProducer.invoke()
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}