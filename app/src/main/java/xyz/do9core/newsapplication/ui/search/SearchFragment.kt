package xyz.do9core.newsapplication.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.chip.Chip
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.fragment.viewObserve
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.extensions.fragment.viewObserveNullable
import xyz.do9core.extensions.lifecycle.valueOrDefault
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.databinding.FragmentSearchBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by stateViewModel()
    private val adapter: ResultAdapter by lifecycleScope.inject { parametersOf(viewModel) }

    override fun createViewBinding(inflater: LayoutInflater): FragmentSearchBinding =
        FragmentSearchBinding.inflate(inflater)

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        with(viewModel) {
            binding.resultList.adapter = adapter
            binding.toolbar.setNavigationOnClickListener {
                frontLayerExpanded.value = !frontLayerExpanded.value!!
            }
            binding.countryButton.setOnClickListener {
                filterDialog<Country>(requireContext()) {
                    title = "Country"
                    items = Country.values()
                    selectionIndex = countryIndex.valueOrDefault(-1)
                    mapItem { country -> country.code.capitalize() }
                    onConfirm { result -> countryIndex.value = result }
                }.show()
            }
            binding.categoryButton.setOnClickListener {
                filterDialog<Category>(requireContext()) {
                    title = "Category"
                    items = Category.values()
                    selectionIndex = categoryIndex.valueOrDefault(-1)
                    mapItem { category -> category.title.capitalize() }
                    onConfirm { result -> categoryIndex.value = result }
                }.show()
            }

            viewObserveEvent(showArticleEvent) { openArticle(it) }
            viewObserve(searchResult) { adapter.submitList(it) }
            viewObserve(frontLayerExpanded) { expanded ->
                if (expanded) {
                    binding.backdrop.transitionToEnd()
                } else {
                    binding.backdrop.transitionToStart()
                }
            }
            viewObserve(navigationIcon) { binding.toolbar.setNavigationIcon(it) }
            viewObserveNullable(country) { country ->
                binding.countryGroup.removeAllViews()
                country?.let {
                    val chip = Chip(requireContext()).apply {
                        text = country.code.capitalize()
                        isCloseIconVisible = true
                        setOnCloseIconClickListener {
                            viewModel.countryIndex.value = -1
                        }
                    }
                    binding.countryGroup.addView(chip)
                }
            }
            viewObserveNullable(category) { category ->
                binding.categoryGroup.removeAllViews()
                category?.let {
                    val chip = Chip(requireContext()).apply {
                        text = category.title.capitalize()
                        isCloseIconVisible = true
                        setOnCloseIconClickListener {
                            viewModel.categoryIndex.value = -1
                        }
                    }
                    binding.categoryGroup.addView(chip)
                }
            }
        }
    }

    private fun openArticle(article: Article) {
        SearchFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}
