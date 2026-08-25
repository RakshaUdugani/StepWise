package com.example.stepwise.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.stepwise.ui.components.AppAccent
import com.example.stepwise.ui.components.AppAccentSoft
import com.example.stepwise.ui.components.AppCanvas
import com.example.stepwise.ui.components.AppPrimary
import com.example.stepwise.ui.components.AppSecondary
import com.example.stepwise.ui.components.CompactSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(userName: String, onOpenBmi: () -> Unit, onOpenWorkout: () -> Unit, onLogout: () -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = AppCanvas,
        topBar = {
            TopAppBar(
                title = { Text("StepWise", color = AppPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                actions = {
                    IconButton(onClick = { menuExpanded = true }) { Icon(Icons.Default.MoreVert, "Account menu", tint = AppPrimary) }
                    DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                        DropdownMenuItem(text = { Text("Logout") }, onClick = { menuExpanded = false; onLogout() })
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().background(AppCanvas).padding(padding).padding(horizontal = 20.dp, vertical = 24.dp)) {
            Text("Good to see you${if (userName.isBlank()) "" else ", $userName"}", style = MaterialTheme.typography.headlineMedium, color = AppPrimary)
            Spacer(Modifier.height(6.dp))
            Text("Small choices today build lasting habits.", style = MaterialTheme.typography.bodyLarge, color = AppSecondary)
            Spacer(Modifier.height(32.dp))
            Text("Your tools", style = MaterialTheme.typography.titleMedium, color = AppPrimary)
            Spacer(Modifier.height(12.dp))
            HomeActionRow("BMI Calculator", "Calculate and save your BMI", Icons.Default.Calculate, onOpenBmi)
            Spacer(Modifier.height(10.dp))
            HomeActionRow("Workout Tracker", "Workouts, exercise packs, and articles", Icons.Default.DirectionsRun, onOpenWorkout)
        }
    }
}

@Composable
private fun HomeActionRow(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    CompactSurface(Modifier.clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(AppAccentSoft), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = AppAccent)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = AppPrimary)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = AppSecondary)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = AppAccent)
        }
    }
}
