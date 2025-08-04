package com.example.newstestapi.repository

import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.model.NewsResponse
import com.example.newstestapi.network.NewsApiService
import com.example.newstestapi.network.RetrofitInstance
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsRepositoryTest {

    private lateinit var repository: NewsRepository
    private lateinit var mockApiService: NewsApiService

    @Before
    fun setup() {
        // Mock the RetrofitInstance object
        mockkObject(RetrofitInstance)

        // Create mock API service
        mockApiService = mockk<NewsApiService>()

        // Configure RetrofitInstance to return our mock
        coEvery { RetrofitInstance.api } returns mockApiService

        // Create repository instance
        repository = NewsRepository()
    }

    @After
    fun tearDown() {
        unmockkObject(RetrofitInstance)
    }

    @Test
    fun test_getTopHeadlines_returns_successful_response_with_articles() = runTest {
        // Arrange
        val expectedArticles = listOf(
            ArticleModel(
                title = "Test Article 1",
                description = "Test Description 1",
                url = "https://test1.com",
                urlToImage = "https://image1.com",
                publishedAt = "2024-01-01T00:00:00Z",
                content = "Test content 1"
            ),
            ArticleModel(
                title = "Test Article 2",
                description = "Test Description 2",
                url = "https://test2.com",
                urlToImage = "https://image2.com",
                publishedAt = "2024-01-02T00:00:00Z",
                content = "Test content 2"
            )
        )

        val expectedResponse = NewsResponse(
            status = "ok",
            totalResults = 2,
            articles = expectedArticles
        )

        val successResponse = Response.success(expectedResponse)
        coEvery { mockApiService.getTopHeadlines() } returns successResponse

        // Act
        val result = repository.getTopHeadlines()

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedResponse, result.body())
        assertEquals(expectedArticles, result.body()?.articles)
        assertEquals(2, result.body()?.totalResults)
        assertEquals("ok", result.body()?.status)
    }

    @Test
    fun test_getTopHeadlines_returns_successful_response_with_empty_articles_list() = runTest {
        // Arrange
        val expectedResponse = NewsResponse(
            status = "ok",
            totalResults = 0,
            articles = emptyList()
        )

        val successResponse = Response.success(expectedResponse)
        coEvery { mockApiService.getTopHeadlines() } returns successResponse

        // Act
        val result = repository.getTopHeadlines()

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(expectedResponse, result.body())
        assertEquals(emptyList<ArticleModel>(), result.body()?.articles)
        assertEquals(0, result.body()?.totalResults)
    }

    @Test
    fun test_getTopHeadlines_returns_error() = runTest {
        // Arrange
        val errorResponse = Response.error<NewsResponse>(
            404,
            "Not Found".toResponseBody(null)
        )
        coEvery { mockApiService.getTopHeadlines() } returns errorResponse

        // Act
        val result = repository.getTopHeadlines()

        // Assert
        assertFalse(result.isSuccessful)
        assertEquals(404, result.code())
    }

    @Test
    fun test_getTopHeadlines_no_network() = runTest {
        // Arrange
        coEvery { mockApiService.getTopHeadlines() } throws Exception("Network error")

        // Act & Assert
        try {
            repository.getTopHeadlines()
            // If we reach here, the test should fail
            assert(false) { "Expected exception was not thrown" }
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
        }
    }

    @Test
    fun test_getTopHeadlines_returns_with_null_body() = runTest {
        // Arrange
        val responseWithNullBody = Response.success<NewsResponse>(null)
        coEvery { mockApiService.getTopHeadlines() } returns responseWithNullBody

        // Act
        val result = repository.getTopHeadlines()

        // Assert
        assertTrue(result.isSuccessful)
        assertEquals(null, result.body())
    }
}