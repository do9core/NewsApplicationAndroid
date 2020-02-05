package xyz.do9core.newsapplication.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.do9core.newsapplication.ui.headline.ArticleAdapter
import xyz.do9core.newsapplication.ui.headline.HeadlineFragment
import xyz.do9core.newsapplication.ui.headline.HeadlineViewModel

val HeadlineFragmentDependency = module {
    scope(named<HeadlineFragment>()) {
        scoped { (viewModel: HeadlineViewModel) -> ArticleAdapter(viewModel) }
    }
}