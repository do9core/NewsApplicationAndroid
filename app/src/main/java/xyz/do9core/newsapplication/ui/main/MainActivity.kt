package xyz.do9core.newsapplication.ui.main

import android.os.Bundle
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.ui.base.ContinuationActivity

class MainActivity : ContinuationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
