package xyz.do9core.newsapplication.ui.common

import xyz.do9core.newsapplication.data.model.Article

typealias ArticleClickedListener = (Article) -> Unit

data class ArticleViewModel(
    val data: Article,
    val clickHandler: ArticleClickedListener,
    val noDropDownButton: Boolean = true,
    val favouriteClicked: ArticleClickedListener? = null,
    val watchLaterClicked: ArticleClickedListener? = null
)