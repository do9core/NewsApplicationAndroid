package xyz.do9core.newsapplication.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.fragment.viewObserve
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.data.model.Country
import xyz.do9core.newsapplication.databinding.FragmentSearchBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate

class ChipGroupController<T : Any>(
    private val context: Context,
    private val title: CharSequence,
    private val chipGroup: ChipGroup,
    private val dataList: List<T>,
    mapper: (T) -> CharSequence = { it.toString() }
) {
    private var checkedIndex = -1
    private val displayArray = dataList.map(mapper).toTypedArray()

    fun showDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setSingleChoiceItems(displayArray, checkedIndex) { _, pos-> checkedIndex = pos }
            .setPositiveButton("OK") { _, _ ->
                chipGroup.removeAllViews()
                val checkedItem = displayArray.getOrNull(checkedIndex) ?: return@setPositiveButton
                val chip = Chip(context).apply {
                    text = checkedItem
                    isCloseIconVisible = true
                    setOnCloseIconClickListener {
                        checkedIndex = -1
                        chipGroup.removeView(it)
                    }
                }
                chipGroup.addView(chip)
            }
            .setNegativeButton("Cancel") { _, _ -> Unit }
            .show()
    }

    fun getSelection(): T? = dataList.getOrNull(checkedIndex)
}

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModel()
    private val adapter: ResultAdapter by lifecycleScope.inject { parametersOf(viewModel) }
    private val backdropController = object {

        private var revealed = true

        fun toggle() {
            if (revealed) showFrontLayer() else showBackLayer()
        }

        fun showFrontLayer(): Unit = with(binding) {
            backdrop.transitionToEnd()
            toolbar.setNavigationIcon(R.drawable.ic_close)
            revealed = false
        }

        fun showBackLayer(): Unit = with(binding) {
            backdrop.transitionToStart()
            toolbar.setNavigationIcon(R.drawable.ic_menu)
            revealed = true
        }
    }
    private val categoryController by lazy {
        ChipGroupController(
            requireContext(),
            "Categories",
            binding.categoryGroup,
            Category.values()
        ) { it.title.capitalize() }
    }
    private val countryController by lazy {
        ChipGroupController(
            requireContext(),
            "Countries",
            binding.countryGroup,
            Country.values()
        ) { it.code.capitalize() }
    }

    override fun createViewBinding(inflater: LayoutInflater): FragmentSearchBinding =
        FragmentSearchBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel = this@SearchFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            resultList.adapter = adapter
            toolbar.setNavigationOnClickListener { backdropController.toggle() }
            countryButton.setOnClickListener { countryController.showDialog() }
            categoryButton.setOnClickListener { categoryController.showDialog() }
            queryText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    this@SearchFragment.viewModel.executeQuery(
                        category = categoryController.getSelection(),
                        country = countryController.getSelection()
                    )
                    hideSoftInput()
                    backdropController.showFrontLayer()
                    true
                } else {
                    false
                }
            }
        }
        with(viewModel) {
            viewObserveEvent(showArticleEvent) { openArticle(it) }
            viewObserve(searchResult) { adapter.submitList(it) }
        }
    }

    private fun hideSoftInput() {
        binding.queryText.clearFocus()
        requireContext()
            .getSystemService<InputMethodManager>()
            ?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun openArticle(article: Article) {
        SearchFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}
