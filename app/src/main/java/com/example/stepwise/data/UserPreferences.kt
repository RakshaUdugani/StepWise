package com.example.stepwise.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("stepwise_prefs", Context.MODE_PRIVATE)

    // LOGIN STATE
    private val _isLoggedIn = MutableStateFlow(prefs.getBoolean("isLoggedIn", false))
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // BMI STATE
    private val _bmi = MutableStateFlow(prefs.getFloat("bmi", 0f))
    val bmi: StateFlow<Float> = _bmi

    fun saveUser(name: String, age: String, gender: String) {
        prefs.edit()
            .putString("name", name)
            .putString("age", age)
            .putString("gender", gender)
            .putBoolean("isLoggedIn", true)
            .apply()
        _isLoggedIn.value = true
    }

    fun logout() {
        prefs.edit()
            .putBoolean("isLoggedIn", false)
            .apply()

        _isLoggedIn.value = false
    }


    fun saveBMI(value: Float) {
        prefs.edit()
            .putFloat("bmi", value)
            .apply()
        _bmi.value = value
    }

    fun getName(): String = prefs.getString("name", "") ?: ""
    fun getAge(): String = prefs.getString("age", "") ?: ""
    fun getGender(): String = prefs.getString("gender", "") ?: ""
}
