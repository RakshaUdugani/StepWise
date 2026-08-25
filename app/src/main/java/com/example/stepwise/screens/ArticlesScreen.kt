package com.example.stepwise.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stepwise.ui.components.AppAccent
import com.example.stepwise.ui.components.AppCanvas
import com.example.stepwise.ui.components.AppPrimary
import com.example.stepwise.ui.components.AppSecondary
import com.example.stepwise.ui.components.CompactSurface
import com.example.stepwise.ui.components.ScreenHeader

@Composable
fun ArticlesScreen(onOpenArticle: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(AppCanvas).padding(20.dp)) {
        ScreenHeader("Health & Fitness Articles", "Practical reading for your everyday fitness journey.")
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(ContentCatalog.articles, key = { it.id }) { article ->
                CompactSurface(Modifier.padding(vertical = 4.dp).clickable { onOpenArticle(article.id) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(article.sourceName.uppercase(), style = MaterialTheme.typography.labelMedium, color = AppAccent)
                            Text(article.title, style = MaterialTheme.typography.titleMedium, color = AppPrimary, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Text(article.summary, style = MaterialTheme.typography.bodySmall, color = AppSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Spacer(Modifier.width(12.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = AppAccent)
                    }
                }
            }
        }
    }
}
