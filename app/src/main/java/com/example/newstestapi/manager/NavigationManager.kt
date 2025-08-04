package com.example.newstestapi.manager

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newstestapi.R
import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.ui.view.DetailScreen
import com.example.newstestapi.ui.view.NewsScreen

@Composable
fun Navigator(
    articles: List<ArticleModel>,
    onGetArticleByEncodedUrl: (String) -> ArticleModel?
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "news") {
        composable("news") {
            val appName: String = stringResource(R.string.app_name)
            NewsScreen(appName, articles, navController)
        }

        composable("detail/{articleUrl}") { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
            val article = onGetArticleByEncodedUrl(encodedUrl)
            if (article != null) {
                DetailScreen(article)
            } else {
                Text("Article not found")
            }
        }
    }
}