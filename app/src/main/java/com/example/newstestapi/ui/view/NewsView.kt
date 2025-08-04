package com.example.newstestapi.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newstestapi.model.ArticleModel
import com.example.newstestapi.ui.components.HeadlineItem
import java.net.URLEncoder

@Composable
fun NewsScreen(providerName: String, articles: List<ArticleModel>, navController: NavController) {

    Column(modifier = Modifier
        .padding(16.dp)
        .statusBarsPadding()
    ) {
        Text(
            text = providerName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .testTag("appName_headerText")
        )

        LazyColumn {
            items(articles) { article ->
                HeadlineItem(article) {
                    val encodedUrl = URLEncoder.encode(article.url, "UTF-8")
                    navController.navigate("detail/$encodedUrl")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
