package xyz.do9core.newsapplication.ui.common

import xyz.do9core.newsapplication.data.model.Article

interface ArticleClickHandler {
    fun onClick(article: Article)
}