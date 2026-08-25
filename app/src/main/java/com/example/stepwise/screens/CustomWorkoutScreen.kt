package com.example.stepwise.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.stepwise.data.CustomWorkoutPreferences
import com.example.stepwise.ui.components.AppAccent
import com.example.stepwise.ui.components.AppCanvas
import com.example.stepwise.ui.components.AppPrimary
import com.example.stepwise.ui.components.AppSecondary
import com.example.stepwise.ui.components.CompactSurface
import com.example.stepwise.ui.components.ScreenHeader

data class WorkoutEntry(val name: String, val duration: Int, val calories: Int)

fun calculateCalories(name: String, duration: Int): Int {
    val met = when (name.trim().lowercase()) {
        "walking" -> 3.5f; "running" -> 9.5f; "cycling" -> 7.5f; "yoga" -> 3.0f
        "strength", "strength training" -> 6.0f; "hiit" -> 8.0f; "dance" -> 5.0f; "aerobics" -> 6.5f
        else -> 5.0f
    }
    return (met * 60f * (duration / 60f)).toInt()
}

@Composable
fun CustomWorkoutScreen() {
    val context = LocalContext.current
    val prefs = remember(context) { CustomWorkoutPreferences(context) }
    var workouts by remember { mutableStateOf(prefs.loadWorkouts()) }
    var name by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var showErrors by remember { mutableStateOf(false) }
    var workoutToDelete by remember { mutableIntStateOf(-1) }
    val durationValue = duration.toIntOrNull()
    val nameError = showErrors && name.isBlank()
    val durationError = showErrors && (durationValue == null || durationValue <= 0)

    Column(Modifier.fillMaxSize().background(AppCanvas).padding(20.dp)) {
        ScreenHeader("Custom Workouts", "Add an activity to estimate its calories.")
        Spacer(Modifier.height(18.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Workout name") }, isError = nameError, supportingText = { if (nameError) Text("Enter a workout name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = duration, onValueChange = { duration = it.filter(Char::isDigit) }, label = { Text("Duration (minutes)") }, isError = durationError, supportingText = { if (durationError) Text("Enter a duration greater than zero") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        Button(onClick = {
            showErrors = true
            if (name.isNotBlank() && durationValue != null && durationValue > 0) {
                workouts = workouts + WorkoutEntry(name.trim(), durationValue, calculateCalories(name, durationValue))
                prefs.saveWorkouts(workouts)
                name = ""; duration = ""; showErrors = false
            }
        }, modifier = Modifier.fillMaxWidth().height(48.dp)) { Text("Add workout") }
        Spacer(Modifier.height(22.dp))
        Text("Your workouts", style = MaterialTheme.typography.titleMedium, color = AppPrimary)
        Spacer(Modifier.height(10.dp))
        if (workouts.isEmpty()) {
            Text("No workouts saved yet. Add one above to begin.", style = MaterialTheme.typography.bodyMedium, color = AppSecondary, modifier = Modifier.padding(vertical = 10.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(workouts, key = { index, workout -> "${workout.name}-${workout.duration}-${workout.calories}-$index" }) { index, workout ->
                    CompactSurface {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(workout.name, style = MaterialTheme.typography.titleMedium, color = AppPrimary)
                                Text("${workout.duration} min  ·  ${workout.calories} kcal", style = MaterialTheme.typography.bodyMedium, color = AppSecondary)
                            }
                            IconButton(onClick = { workoutToDelete = index }, modifier = Modifier.size(40.dp)) { Icon(Icons.Default.Delete, "Delete ${workout.name}", tint = AppAccent) }
                        }
                    }
                }
            }
        }
    }
    if (workoutToDelete in workouts.indices) {
        AlertDialog(onDismissRequest = { workoutToDelete = -1 }, title = { Text("Delete workout?") }, text = { Text("Remove ${workouts[workoutToDelete].name} from your saved workouts?") }, confirmButton = {
            TextButton(onClick = { prefs.deleteWorkout(workoutToDelete); workouts = prefs.loadWorkouts(); workoutToDelete = -1 }) { Text("Delete") }
        }, dismissButton = { TextButton(onClick = { workoutToDelete = -1 }) { Text("Cancel") } })
    }
}
