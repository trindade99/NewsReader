package com.example.newstestapi.ui.components

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.newstestapi.R
import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.ui.view.NewsScreen
import org.junit.Rule
import org.junit.Test

class NewsViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun newsScreen_displays_appName_article() {
        // Arrange: Create mock article
        val article = ArticleModel(
            title = "Test Title",
            description = "This is the description",
            content = "This is the content",
            urlToImage = "https://example.com/image.jpg",
            url = "https://example.com",
            publishedAt = ""
        )
        var appName = ""

        // Act: Set the composable under test
        composeTestRule.setContent {
            appName = stringResource(R.string.app_name)
            val navController = rememberNavController()
            NewsScreen(appName, listOf(article), navController)
        }

        // Assert: Check texts are displayed
        composeTestRule
            .onNodeWithText(appName)
            .assertExists()

        composeTestRule
            .onNodeWithText("Test Title")
            .assertExists()
    }

}
