package xyz.do9core.newsapplication.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import xyz.do9core.coroutineutils.exclusive.onExclusiveClick
import xyz.do9core.newsapplication.R

@BindingAdapter("isVisible")
fun View.bindIsVisible(visible: Boolean?) {
    visibility = if (visible == null || !visible) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isGone")
fun View.isGone(gone: Boolean?) {
    visibility = if (gone == null || gone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isInvisible")
fun View.isInvisible(invisible: Boolean?) {
    visibility = if (invisible == null || invisible) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
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