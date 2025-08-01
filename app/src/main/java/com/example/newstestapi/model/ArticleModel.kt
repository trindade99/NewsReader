package com.example.newstestapi.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleModel>
)

data class ArticleModel(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val url: String
)
