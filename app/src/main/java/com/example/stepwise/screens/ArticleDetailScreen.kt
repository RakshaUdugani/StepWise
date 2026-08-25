package com.example.stepwise.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(articleId: String, onBack: () -> Unit) {
    val article = ContentCatalog.article(articleId)
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { Text("Article") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
        })
    }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF3E5F5)).padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (article == null) {
                Text("Article not found", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = onBack) { Text("Return to articles") }
            } else {
                Text(article.title, style = MaterialTheme.typography.headlineSmall, color = Color(0xFF4A148C))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6))) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(article.summary, style = MaterialTheme.typography.titleMedium)
                        Text(article.content, style = MaterialTheme.typography.bodyLarge)
                        Text("Source: ${article.sourceName}", style = MaterialTheme.typography.labelLarge)
                    }
                }
                Button(onClick = {
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, article.link.toUri()))
                    } catch (_: Exception) { }
                }, modifier = Modifier.fillMaxWidth()) { Text("Open source") }
            }
        }
    }
}
