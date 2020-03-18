package xyz.do9core.newsapplication.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.fragment.viewObserve
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.extensions.fragment.viewObserveNullable
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.databinding.FragmentSearchBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()
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
                var tempIndex = countryIndex.value!!
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Country")
                    .setSingleChoiceItems(
                        Country.values().map { it.code.capitalize() }.toTypedArray(),
                        tempIndex
                    ) { _, pos -> tempIndex = pos }
                    .setPositiveButton("OK") { _, _ -> countryIndex.value = tempIndex }
                    .create()
                    .show()
            }
            binding.categoryButton.setOnClickListener {
                var tempIndex = categoryIndex.value!!
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Category")
                    .setSingleChoiceItems(
                        Category.values().map { it.title.capitalize() }.toTypedArray(),
                        tempIndex
                    ) { _, pos -> tempIndex = pos }
                    .setPositiveButton("OK") { _, _ -> categoryIndex.value = tempIndex }
                    .create()
                    .show()
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
