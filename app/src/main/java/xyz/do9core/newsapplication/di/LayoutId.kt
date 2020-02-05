package xyz.do9core.newsapplication.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.do9core.newsapplication.R

object LayoutIdName {

    const val Main = "activity_main"
    const val MainHost = "fragment_main"
    const val AppInfo = "fragment_app_info"
    const val Article = "fragment_article"
    const val WebView = "fragment_webview"
    const val Favourite = "fragment_favourite"
    const val Headline = "fragment_headline"
    const val WatchLater = "fragment_watch_later"
}

val LayoutIdModule = module {
    single(named(LayoutIdName.Main)) { R.layout.activity_main }
    single(named(LayoutIdName.MainHost)) { R.layout.fragment_main }
    single(named(LayoutIdName.AppInfo)) { R.layout.fragment_app_info }
    single(named(LayoutIdName.Article)) { R.layout.fragment_article }
    single(named(LayoutIdName.WebView)) { R.layout.fragment_webview }
    single(named(LayoutIdName.Favourite)) { R.layout.fragment_favourite }
    single(named(LayoutIdName.Headline)) { R.layout.fragment_headline }
    single(named(LayoutIdName.WatchLater)) { R.layout.fragment_watch_later }
}