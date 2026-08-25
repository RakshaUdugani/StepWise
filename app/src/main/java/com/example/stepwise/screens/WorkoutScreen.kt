package com.example.stepwise.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.stepwise.ui.components.AppAccent
import com.example.stepwise.ui.components.AppAccentSoft
import com.example.stepwise.ui.components.AppCanvas
import com.example.stepwise.ui.components.AppPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(onOpenExercise: (String) -> Unit, onOpenArticle: (String) -> Unit) {
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val tabs = listOf("Steps", "Custom", "Exercises", "Articles")
    val icons = listOf(Icons.Filled.DirectionsRun, Icons.Filled.FitnessCenter, Icons.Filled.List, Icons.Filled.Article)
    Scaffold(
        containerColor = AppCanvas,
        topBar = { TopAppBar(title = { Text("Workout Tracker", color = AppPrimary) }) },
        bottomBar = { NavigationBar { tabs.forEachIndexed { index, label ->
            NavigationBarItem(selected = selectedTab == index, onClick = { selectedTab = index }, icon = { Icon(icons[index], label) }, label = { Text(label) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = AppPrimary, selectedTextColor = AppPrimary, indicatorColor = AppAccentSoft, unselectedIconColor = Color(0xFF5F5964), unselectedTextColor = Color(0xFF5F5964)))
        } } }
    ) { padding ->
        Box(Modifier.fillMaxSize().background(AppCanvas).padding(padding)) {
            when (selectedTab) {
                0 -> StepCountScreen(activity = LocalContext.current as Activity)
                1 -> CustomWorkoutScreen()
                2 -> ExerciseListScreen(onOpenExercise)
                3 -> ArticlesScreen(onOpenArticle)
            }
        }
    }
}
