package xyz.do9core.newsapplication.ui.main

import androidx.lifecycle.ViewModel
import xyz.do9core.extensions.lifecycle.EventLiveData

class SharedViewModel : ViewModel() {

    val showSnackbarEvent = EventLiveData<String>()
    val showErrorEvent = EventLiveData<Int>()
}