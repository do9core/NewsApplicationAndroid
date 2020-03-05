package xyz.do9core.newsapplication.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(direction: NavDirections) =
    findNavController().navigate(direction)

fun Fragment.navigateUp() =
    findNavController().navigateUp()