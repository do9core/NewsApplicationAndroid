package xyz.do9core.newsapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.do9core.newsapplication.util.Event

class SharedViewModel : ViewModel() {

    val showSnackbarEvent = MutableLiveData<Event<String>>()
    val showErrorEvent = MutableLiveData<Event<Int>>()
}