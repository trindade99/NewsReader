package com.example.newstestapi.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.repository.NewsRepository
import com.example.newstestapi.repository.NewsRepositoryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewsViewModel(private val repository: NewsRepositoryType = NewsRepository()) : ViewModel() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
    private val _articles = MutableStateFlow<List<ArticleModel>>(emptyList())
    val articles: Flow<List<ArticleModel>> = _articles.asStateFlow()

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val response = repository.getTopHeadlines()
                if (response.isSuccessful) {
                    _articles.value = response.body()?.articles?.sortedByDescending {
                        LocalDateTime.parse(it.publishedAt, formatter)
                    } ?: emptyList()
                } else {
                    _articles.value = emptyList()
                    Log.w("NewsViewModel", "API error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Exception during fetch: ${e.message}", e)
            }
        }
    }

    fun getArticleByUrl(articleUrl: String): ArticleModel? {
        return _articles.value.find { article -> article.url == articleUrl }
    }

    fun getArticles(): List<ArticleModel> {
        return _articles.value
    }
}