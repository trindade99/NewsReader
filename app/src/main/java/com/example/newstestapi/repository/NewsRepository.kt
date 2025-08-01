package com.example.newstestapi.repository

import com.example.newstestapi.model.NewsResponse
import com.example.newstestapi.network.RetrofitInstance
import retrofit2.Response

interface NewsRepositoryType {
    suspend fun getTopHeadlines(): Response<NewsResponse>
}

class NewsRepository: NewsRepositoryType {
    override suspend fun getTopHeadlines() =
        RetrofitInstance.api.getTopHeadlines()
}
