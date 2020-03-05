package xyz.do9core.newsapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import xyz.do9core.newsapplication.di.*

@Suppress("unused")
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@NewsApplication)
            modules(
                DataModule,
                ViewModelModule,
                SearchFragmentDependency,
                FavouriteFragmentDependency,
                HeadlineFragmentDependency,
                WatchLaterFragmentDependency
            )
        }
    }
}