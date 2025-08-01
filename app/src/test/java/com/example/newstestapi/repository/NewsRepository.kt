package com.example.newstestapi.repository

import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.model.NewsResponse
import retrofit2.Response

class MockNewsRepository(
    private val articles: List<ArticleModel>,
    private val responseBuilder: (NewsResponse) -> Response<NewsResponse>
) : NewsRepositoryType {

    override suspend fun getTopHeadlines(): Response<NewsResponse> =
        responseBuilder(NewsResponse("200", articles.size, articles))
}
