package xyz.do9core.newsapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import xyz.do9core.newsapplication.di.DataModule
import xyz.do9core.newsapplication.di.ViewModelModule
import xyz.do9core.newsapplication.di.modules

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(DataModule, ViewModelModule)
        }
    }
}