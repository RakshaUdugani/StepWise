package com.example.stepwise.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.loginDataStore by preferencesDataStore("login_prefs")

class LoginPreferences(private val context: Context) {

    companion object {
        private val KEY_NAME = stringPreferencesKey("user_name")
        private val KEY_AGE = intPreferencesKey("user_age")
        private val KEY_GENDER = stringPreferencesKey("user_gender")
        private val KEY_LOGGED_IN = booleanPreferencesKey("logged_in")
    }

    // Save user data
    suspend fun saveUser(name: String, age: Int, gender: String) {
        context.loginDataStore.edit { prefs ->
            prefs[KEY_NAME] = name
            prefs[KEY_AGE] = age
            prefs[KEY_GENDER] = gender
            prefs[KEY_LOGGED_IN] = true
        }
    }

    // Logout user
    suspend fun setLoggedIn(value: Boolean) {
        context.loginDataStore.edit { prefs ->
            prefs[KEY_LOGGED_IN] = value
        }

        // 🔥 CLEAR CUSTOM WORKOUTS ON LOGOUT
        if (!value) {
            CustomWorkoutPreferences(context).clearWorkouts()
        }
    }

    // Read values
    val userName: Flow<String> = context.loginDataStore.data.map { it[KEY_NAME] ?: "" }
    val userAge: Flow<Int> = context.loginDataStore.data.map { it[KEY_AGE] ?: 0 }
    val userGender: Flow<String> = context.loginDataStore.data.map { it[KEY_GENDER] ?: "" }
    val isLoggedIn: Flow<Boolean> = context.loginDataStore.data.map { it[KEY_LOGGED_IN] ?: false }
}
