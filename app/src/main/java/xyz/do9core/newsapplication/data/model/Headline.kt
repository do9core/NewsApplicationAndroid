package xyz.do9core.newsapplication.data.model

data class Headline(
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)
