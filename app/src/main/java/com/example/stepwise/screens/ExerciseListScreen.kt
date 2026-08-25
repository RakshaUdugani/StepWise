package com.example.stepwise.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stepwise.ui.components.AppAccent
import com.example.stepwise.ui.components.AppAccentSoft
import com.example.stepwise.ui.components.AppCanvas
import com.example.stepwise.ui.components.AppPrimary
import com.example.stepwise.ui.components.AppSecondary
import com.example.stepwise.ui.components.CompactSurface
import com.example.stepwise.ui.components.ScreenHeader

@Composable
fun ExerciseListScreen(onOpenPack: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(AppCanvas).padding(20.dp)) {
        ScreenHeader("Exercise Packs", "Choose a focused routine to get started.")
        Spacer(Modifier.size(16.dp))
        LazyColumn {
            items(ContentCatalog.exercisePacks, key = { it.id }) { pack ->
                CompactSurface(Modifier.padding(vertical = 4.dp).clickable { onOpenPack(pack.id) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(34.dp).clip(CircleShape).background(AppAccentSoft), contentAlignment = Alignment.Center) {
                            Text("${pack.workouts.size}", style = MaterialTheme.typography.labelLarge, color = AppAccent)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(pack.title, style = MaterialTheme.typography.titleMedium, color = AppPrimary)
                            Text(pack.description, style = MaterialTheme.typography.bodyMedium, color = AppSecondary, maxLines = 1)
                            Text("${pack.workouts.size} exercises", style = MaterialTheme.typography.labelMedium, color = AppAccent)
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = AppAccent)
                    }
                }
            }
        }
    }
}
