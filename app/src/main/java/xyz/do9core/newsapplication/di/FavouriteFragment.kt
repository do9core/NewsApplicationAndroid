package xyz.do9core.newsapplication.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.do9core.newsapplication.ui.favourite.ArticleAdapter
import xyz.do9core.newsapplication.ui.favourite.FavouriteFragment
import xyz.do9core.newsapplication.ui.favourite.FavouriteViewModel

val FavouriteFragmentDependency = module {
    scope(named<FavouriteFragment>()) {
        scoped { (viewModel: FavouriteViewModel) -> ArticleAdapter(viewModel) }
    }
}