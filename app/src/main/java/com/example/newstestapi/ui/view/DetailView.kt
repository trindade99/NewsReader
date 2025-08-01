package com.example.newstestapi.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newstestapi.model.ArticleModel

/**
 * DetailScreen displays detailed information about an [ArticleModel],
 * showing a subtly background image and the article content.
 */
@Composable
fun DetailScreen(article: ArticleModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        article.urlToImage?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.15f)
                    .testTag("backgroundImage"),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            article.urlToImage?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ){
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineLarge
                )
                article.description?.let { description ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                article.content?.let { content ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
