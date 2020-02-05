package xyz.do9core.newsapplication.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.do9core.newsapplication.ui.watchlater.ArticleAdapter
import xyz.do9core.newsapplication.ui.watchlater.WatchLaterFragment
import xyz.do9core.newsapplication.ui.watchlater.WatchLaterViewModel

val WatchLaterFragmentDependency = module {
    scope(named<WatchLaterFragment>()) {
        scoped { (viewModel: WatchLaterViewModel) -> ArticleAdapter(viewModel) }
    }
}