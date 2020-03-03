package xyz.do9core.newsapplication.ui.favourite

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.lifecycle.observe
import xyz.do9core.extensions.lifecycle.observeEvent
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.FragmentFavouriteBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.util.navigate
import xyz.do9core.newsapplication.util.navigateUp

class FavouriteFragment : BindingFragment<FragmentFavouriteBinding>() {

    private val viewModel by viewModel<FavouriteViewModel>()
    private val adapter: ArticleAdapter by currentScope.inject { parametersOf(viewModel) }

    override val layoutResId: Int = R.layout.fragment_favourite

    override fun setupBinding(binding: FragmentFavouriteBinding) {
        binding.lifecycleOwner = this
        binding.toolbar.setNavigationOnClickListener { navigateUp() }
        binding.favouriteList.layoutManager = LinearLayoutManager(requireContext())
        binding.favouriteList.adapter = adapter
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fav_clear -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.app_clear_fav_title)
                        .setMessage(R.string.app_clear_fav_message)
                        .setPositiveButton(R.string.app_clear_fav_positive) { _, _ ->
                            this@FavouriteFragment.viewModel.clearFavourites()
                        }
                        .setNegativeButton(R.string.app_clear_fav_negative) { _, _ -> }
                        .show()
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
    }

    override fun setupObservers() = with(viewModel) {
        observeEvent(showBrowserEvent) { showFavourite(it) }
        observe(favArticles) { adapter.submitList(it) }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadFavourites()
    }

    private fun showFavourite(article: Article) {
        FavouriteFragmentDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }
}