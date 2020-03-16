package xyz.do9core.newsapplication.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import xyz.do9core.coroutineutils.view.onExclusiveClick
import xyz.do9core.newsapplication.R

@BindingAdapter("isVisible")
fun View.bindIsVisible(visible: Boolean?) {
    this.isVisible = visible ?: false
}

@BindingAdapter("isGone")
fun View.isGone(gone: Boolean?) {
    this.isGone = gone ?: true
}

@BindingAdapter("isInvisible")
fun View.isInvisible(invisible: Boolean?) {
    this.isInvisible = invisible ?: true
}

private const val DEFAULT_EXCLUSIVE_DURATION = 1000L

@BindingAdapter(value = ["onExclusiveClick", "exclusiveDuration"], requireAll = false)
fun View.bindOnExclusiveClick(
    onExclusiveClick: View.OnClickListener,
    exclusiveDurationInMs: Long?
) = onExclusiveClick(exclusiveDurationInMs ?: DEFAULT_EXCLUSIVE_DURATION) {
    onExclusiveClick.onClick(this)
}

@BindingAdapter("urlToImage")
fun ImageView.bindUrl(urlToImage: String?) {
    Glide.with(this).load(urlToImage).placeholder(R.drawable.ic_placeholder).into(this)
}

@BindingAdapter("onRefresh")
fun SwipeRefreshLayout.bindOnRefresh(onRefresh: SwipeRefreshLayout.OnRefreshListener) {
    this.setOnRefreshListener(onRefresh)
}

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.bindRefreshing(refreshing: Boolean?) {
    this.isRefreshing = refreshing ?: false
}