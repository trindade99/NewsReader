package com.example.newstestapi.manager

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newstestapi.R
import com.example.newstestapi.ui.view.DetailScreen
import com.example.newstestapi.ui.view.NewsScreen
import com.example.newstestapi.viewModel.NewsViewModel
import java.net.URLDecoder

@Composable
fun Navigator(viewModel: NewsViewModel) {
    val articles by viewModel.articles.collectAsState()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "news") {
        composable("news") {
            val appName: String = stringResource(R.string.app_name)
            NewsScreen(appName, articles, navController)
        }

        composable("detail/{articleUrl}") { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
            val decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8")
            val article = viewModel.getArticleByUrl(decodedUrl)
            if (article != null) {
                DetailScreen(article)
            } else {
                Text("Article not found")
            }
        }
    }
}