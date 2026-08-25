package com.example.stepwise.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stepwise.data.UserPreferences
import com.example.stepwise.screens.ArticleDetailScreen
import com.example.stepwise.screens.ArticlesScreen
import com.example.stepwise.screens.BMICalculatorScreen
import com.example.stepwise.screens.BMIViewModel
import com.example.stepwise.screens.CustomWorkoutScreen
import com.example.stepwise.screens.ExerciseDetailScreen
import com.example.stepwise.screens.ExerciseListScreen
import com.example.stepwise.screens.HomeScreen
import com.example.stepwise.screens.LoginScreen
import com.example.stepwise.screens.StepCountScreen
import com.example.stepwise.screens.WorkoutScreen

private object Route {
    const val Home = "home"
    const val Bmi = "bmi"
    const val Workout = "workout"
    const val Steps = "steps"
    const val ExerciseDetail = "exerciseDetail/{packId}"
    const val ArticleDetail = "articleDetail/{articleId}"
    fun exerciseDetail(id: String) = "exerciseDetail/$id"
    fun articleDetail(id: String) = "articleDetail/$id"
}

@Composable
fun StepWiseApp() {
    val context = LocalContext.current
    val prefs = remember(context) { UserPreferences(context.applicationContext) }
    val isLoggedIn by prefs.isLoggedIn.collectAsState()

    key(isLoggedIn) {
        if (!isLoggedIn) {
            LoginScreen(prefs)
        } else {
            val navController = rememberNavController()
            val bmiViewModel = remember { BMIViewModel(context.applicationContext) }
            NavHost(navController = navController, startDestination = Route.Home) {
                composable(Route.Home) {
                    HomeScreen(prefs.getName(), onOpenBmi = { navController.navigate(Route.Bmi) }, onOpenWorkout = { navController.navigate(Route.Workout) }, onLogout = { prefs.logout() })
                }
                composable(Route.Bmi) { BMICalculatorScreen(bmiViewModel) }
                composable(Route.Workout) {
                    WorkoutScreen(onOpenExercise = { navController.navigate(Route.exerciseDetail(it)) }, onOpenArticle = { navController.navigate(Route.articleDetail(it)) })
                }
                composable(Route.Steps) {
                    StepCountScreen(activity = LocalContext.current as Activity)
                }
                composable(Route.ExerciseDetail) { entry ->
                    ExerciseDetailScreen(entry.arguments?.getString("packId").orEmpty(), navController::popBackStack)
                }
                composable(Route.ArticleDetail) { entry ->
                    ArticleDetailScreen(entry.arguments?.getString("articleId").orEmpty(), navController::popBackStack)
                }
                composable("customWorkout") { CustomWorkoutScreen() }
                composable("exercises") { ExerciseListScreen { navController.navigate(Route.exerciseDetail(it)) } }
                composable("articles") { ArticlesScreen { navController.navigate(Route.articleDetail(it)) } }
            }
        }
    }
}
