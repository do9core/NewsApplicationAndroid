package xyz.do9core.newsapplication.ui.headline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import xyz.do9core.extensions.fragment.viewObserve
import xyz.do9core.extensions.fragment.viewObserveEvent
import xyz.do9core.liveeventbus.eventbus.LiveEventBus
import xyz.do9core.liveeventbus.eventbus.withKey
import xyz.do9core.newsapplication.NavGraphDirections
import xyz.do9core.newsapplication.R
import xyz.do9core.newsapplication.data.model.Article
import xyz.do9core.newsapplication.data.model.Category
import xyz.do9core.newsapplication.databinding.FragmentHeadlineBinding
import xyz.do9core.newsapplication.ui.base.BindingFragment
import xyz.do9core.newsapplication.ui.main.MainFragment
import xyz.do9core.newsapplication.util.navigate

class HeadlineFragment : BindingFragment<FragmentHeadlineBinding>() {

    private val adapter: ArticleAdapter by lifecycleScope.inject { parametersOf(viewModel) }
    private val viewModel by viewModel<HeadlineViewModel> { parametersOf(getCategory()) }

    override fun createViewBinding(inflater: LayoutInflater): FragmentHeadlineBinding =
        FragmentHeadlineBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadArticles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.articleList.adapter = adapter
        with(viewModel) {
            viewObserve(articles) { adapter.submitList(it) }
            viewObserve(networkState) { adapter.setLoadState(it) }
            viewObserveEvent(showArticleEvent) { showArticle(it) }
            LiveEventBus.withKey(MainFragment) {
                viewObserveEvent(messageSnackbarEvent) { postStickyNow(it) }
                viewObserveEvent(imageSavedEvent) { postStickyNow(it) }
                viewObserveEvent(errorSnackbarEvent) {
                    val msg = it.takeIf { it.isNotBlank() }
                        ?: getString(R.string.app_save_favourite_failed)
                    postStickyNow(msg)
                }
            }
        }
    }

    private fun getCategory(): Category {
        return requireArguments()[KEY_CATEGORY] as Category
    }

    private fun showArticle(article: Article) {
        NavGraphDirections.showArticle(
            articleUrl = article.url,
            articleTitle = article.title
        ).let { navigate(it) }
    }

    companion object {
        const val KEY_CATEGORY = "HeadlineFragment.KEY_CATEGORY"
    }
}