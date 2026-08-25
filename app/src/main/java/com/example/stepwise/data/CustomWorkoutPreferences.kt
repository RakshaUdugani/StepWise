package com.example.stepwise.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.stepwise.screens.WorkoutEntry

class CustomWorkoutPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("custom_workout_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveWorkouts(list: List<WorkoutEntry>) {
        val json = gson.toJson(list)
        prefs.edit().putString("workout_list", json).apply()
    }
    fun deleteWorkout(index: Int) {
        val current = loadWorkouts().toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            saveWorkouts(current)
        }
    }


    fun loadWorkouts(): List<WorkoutEntry> {
        val json = prefs.getString("workout_list", null) ?: return emptyList()

        val type = object : TypeToken<List<WorkoutEntry>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearWorkouts() {
        prefs.edit().clear().apply()
    }
}
