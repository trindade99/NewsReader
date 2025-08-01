package com.example.newstestapi.viewModel

import MainDispatcherRule
import com.example.newstestapi.repository.MockNewsRepository
import android.util.Log
import com.example.newstestapi.model.ArticleModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import io.mockk.mockkStatic
import io.mockk.every
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.w(any<String>(), any<String>()) } returns 0
    }

    @Test
    fun test_news_view_model_success_response() = runTest {
        val articlesList = listOf(ArticleModel("Test title", "", "", "", "", "test.url"))
        val mockRepo = MockNewsRepository(articlesList) { newsResponse ->
            Response.success(newsResponse)
        }
        val subject = NewsViewModel(repository = mockRepo)
        subject.fetchArticles()

        val articlesFromFlow = subject.getArticles()
        assertEquals(articlesFromFlow, articlesList)
    }

    @Test
    fun test_news_view_model_fail_response() = runTest {
        val mockRepo = MockNewsRepository(emptyList()) { _ ->
            Response.error(
                404,
                "Not Found".toResponseBody(null)
            )
        }
        val subject = NewsViewModel(repository = mockRepo)
        subject.fetchArticles()

        val articlesFromFlow = subject.getArticles()
        val expected: List<ArticleModel> = emptyList()
        assertEquals(articlesFromFlow, expected)
    }
}
