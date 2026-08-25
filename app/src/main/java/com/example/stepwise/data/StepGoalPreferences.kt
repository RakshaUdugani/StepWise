package com.example.stepwise.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StepGoalPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("step_goal_prefs", Context.MODE_PRIVATE)

    private val _goal = MutableStateFlow(prefs.getInt("daily_goal", 0))
    val goal: StateFlow<Int> = _goal

    fun saveGoal(value: Int) {
        prefs.edit().putInt("daily_goal", value).apply()
        _goal.value = value
    }

    fun clearGoal() {
        prefs.edit().putInt("daily_goal", 0).apply()
        _goal.value = 0
    }
}
