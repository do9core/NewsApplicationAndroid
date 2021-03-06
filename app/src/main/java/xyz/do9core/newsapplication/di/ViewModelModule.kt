package xyz.do9core.newsapplication.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.ui.favourite.FavouriteViewModel
import xyz.do9core.newsapplication.ui.headline.HeadlineViewModel
import xyz.do9core.newsapplication.ui.search.SearchViewModel

val ViewModelModule = module {
    viewModel { FavouriteViewModel(get()) }
    viewModel { SearchViewModel() }
    viewModel { (category: Category) -> HeadlineViewModel(category, get(), get()) }
}