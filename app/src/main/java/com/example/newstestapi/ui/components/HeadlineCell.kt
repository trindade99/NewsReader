package com.example.newstestapi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newstestapi.model.ArticleModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HeadlineItem(article: ArticleModel, onClick: () -> Unit) {
    val dateInputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val dateOutputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")

    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp),
        )
        .testTag("contentColumn")
        .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
        .clip(RoundedCornerShape(12.dp))
        .padding(16.dp)
    ) {
        if (article.urlToImage != null) {
            Image(
                painter =  rememberAsyncImagePainter(model = article.urlToImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("headerImage")
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
            text = article.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .testTag("headline_titleText"),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = LocalDateTime.parse(article.publishedAt, dateInputFormatter).format(dateOutputFormatter),
            modifier = Modifier
                .testTag("dateText"),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
