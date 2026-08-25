package com.example.stepwise.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp

data class WorkoutDetail(val name: String, val duration: String, val calories: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(packId: String, onBack: () -> Unit) {
    val pack = ContentCatalog.exercisePack(packId)
    Scaffold(topBar = {
        TopAppBar(title = { Text(pack?.title ?: "Exercise pack") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
        })
    }) { padding ->
        if (pack == null) {
            Column(Modifier.fillMaxSize().background(Color(0xFFF3E5F5)).padding(padding).padding(20.dp)) {
                Text("This exercise pack is unavailable.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Color(0xFFF3E5F5)).padding(padding).padding(horizontal = 20.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 14.dp)
            ) {
                item {
                    Text(pack.description, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 10.dp))
                }
                items(pack.workouts, key = { it.name }) { workout ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
                        modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(workout.name, style = MaterialTheme.typography.titleMedium, color = Color(0xFF6A1B9A))
                            Text("Sets / duration: ${workout.duration}", style = MaterialTheme.typography.bodyMedium)
                            Text("Estimated energy: ${workout.calories}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
