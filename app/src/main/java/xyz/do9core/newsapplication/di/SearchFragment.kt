package xyz.do9core.newsapplication.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.do9core.newsapplication.ui.search.ResultAdapter
import xyz.do9core.newsapplication.ui.search.SearchFragment
import xyz.do9core.newsapplication.ui.search.SearchViewModel

val SearchFragmentDependency = module {
    scope(named<SearchFragment>()) {
        scoped { (viewModel: SearchViewModel) -> ResultAdapter(viewModel) }
    }
}
