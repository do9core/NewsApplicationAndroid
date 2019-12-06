package xyz.do9core.newsapplication.ui.favourite

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import xyz.do9core.newsapplication.NewsApplication
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.databinding.ActivityFavouriteBinding
import xyz.do9core.newsapplication.ui.article.ArticleActivity
import xyz.do9core.newsapplication.ui.base.BindLayout
import xyz.do9core.newsapplication.ui.base.BindingActivity
import xyz.do9core.newsapplication.util.observeEvent

@BindLayout(R.layout.activity_favourite)
class FavouriteActivity : BindingActivity<ActivityFavouriteBinding>() {

    private val viewModel: FavouriteViewModel by viewModels {
        FavouriteViewModel.Factory(application as NewsApplication)
    }

    private val adapter by lazy { ArticleAdapter(viewModel) }

    override fun setupBinding(binding: ActivityFavouriteBinding) = with(binding) {
        favouriteList.layoutManager = LinearLayoutManager(this@FavouriteActivity)
        favouriteList.adapter = adapter
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.fav_clear -> {
                    MaterialAlertDialogBuilder(this@FavouriteActivity)
                        .setTitle(R.string.app_clear_fav_title)
                        .setMessage(R.string.app_clear_fav_message)
                        .setPositiveButton(R.string.app_clear_fav_positive) { _, _ ->
                            this@FavouriteActivity.viewModel.clearFavourites()
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }

    private fun showFavourite(article: Article) {
        ArticleActivity.start(this, article.url, article.title)
    }
}