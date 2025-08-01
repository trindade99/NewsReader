package com.example.newstestapi.network

import com.example.newstestapi.BuildConfig
import com.example.newstestapi.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("sources") sources: String = BuildConfig.NEWS_SOURCE
    ): Response<NewsResponse>
}
