package com.example.newstestapi.ui.components

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.ui.view.DetailScreen
import org.junit.Rule
import org.junit.Test

class DetailViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val testArticle = ArticleModel(
        title = "Test Title",
        description = "This is the description",
        content = "This is the content",
        urlToImage = "https://example.com/image.jpg",
        url = "https://example.com",
        publishedAt = ""
    )

    @Test
    fun detailScreen_displays_title_description_content() {
        // Arrange: Create test article
        val article = testArticle

        // Act: Set the composable under test
        composeTestRule.setContent {
            DetailScreen(article)
        }


        // Assert: Check texts are displayed
        composeTestRule
            .onNode(hasTestTag("titleText"))
            .assertExists()

        composeTestRule
            .onNode(hasTestTag("descriptionText"))
            .assertExists()

        composeTestRule
            .onNode(hasTestTag("contentText"))
            .assertExists()
    }

    @Test
    fun detailScreen_displays_image() {
        // Arrange: Create test article
        val article = ArticleModel(
            title = "Test Title",
            description = "This is the description",
            content = "This is the content",
            urlToImage = "https://picsum.photos/200/300",
            url = "https://example.com",
            publishedAt = ""
        )

        // Act: Set the composable under test
        composeTestRule.setContent {
            DetailScreen(article)
        }

        // Assert: Check image exists
        composeTestRule
            .onNode(hasTestTag("backgroundImage"))
            .assertExists()
    }

    @Test
    fun detailScreen_not_displays_image() {
        // Arrange: Create test article
        val article = ArticleModel(
            title = "Test Title",
            description = "This is the description",
            content = "This is the content",
            urlToImage = null,
            url = "https://example.com",
            publishedAt = ""
        )

        // Act: Set the composable under test
        composeTestRule.setContent {
            DetailScreen(article)
        }

        // Assert: Check images do not exists
        composeTestRule
            .onNode(hasTestTag("backgroundImage"))
            .assertDoesNotExist()
    }
}
