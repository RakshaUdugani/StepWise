package com.example.stepwise.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.stepwise.data.StepGoalPreferences
import com.example.stepwise.healthconnect.HealthConnectAvailability
import com.example.stepwise.healthconnect.HealthConnectManager
import com.example.stepwise.permissions.StepPermissions
import kotlinx.coroutines.launch

enum class StepDataStatus {
    Checking,
    Ready,
    PermissionRequired,
    Unavailable,
    UpdateRequired,
    Error
}

class StepCounterViewModel(application: Application) : AndroidViewModel(application) {

    private val healthConnect = HealthConnectManager(application)
    private val goalPrefs = StepGoalPreferences(application)

    val todaySteps = MutableLiveData(0)
    val weeklySteps = MutableLiveData<List<Int>>(List(7) { 0 })
    val stepStatus = MutableLiveData(StepDataStatus.Checking)
    val statusMessage = MutableLiveData<String?>(null)

    val dailyGoal = goalPrefs.goal

    fun saveDailyGoal(goal: Int) {
        goalPrefs.saveGoal(goal)
    }

    fun clearGoal() {
        goalPrefs.clearGoal()
    }

    fun permissionContract() = healthConnect.permissionContract()

    fun installOrUpdateIntent() = healthConnect.installOrUpdateIntent()

    fun checkAndLoad() {
        viewModelScope.launch {
            when (healthConnect.availability()) {
                HealthConnectAvailability.UNAVAILABLE -> {
                    stepStatus.value = StepDataStatus.Unavailable
                    statusMessage.value = "Health Connect is not available on this device."
                }
                HealthConnectAvailability.UPDATE_REQUIRED -> {
                    stepStatus.value = StepDataStatus.UpdateRequired
                    statusMessage.value = "Install or update Health Connect to see your steps."
                }
                HealthConnectAvailability.AVAILABLE -> {
                    val granted = try {
                        healthConnect.hasReadStepsPermission()
                    } catch (e: Exception) {
                        stepStatus.value = StepDataStatus.Error
                        statusMessage.value = e.localizedMessage ?: "Could not check Health Connect permission."
                        return@launch
                    }
                    if (!granted) {
                        stepStatus.value = StepDataStatus.PermissionRequired
                        statusMessage.value = "StepWise needs Health Connect permission to read your steps."
                    } else {
                        loadStepData()
                    }
                }
            }
        }
    }

    fun onPermissionResult(granted: Set<String>) {
        if (StepPermissions.READ_STEPS.all { it in granted }) {
            viewModelScope.launch { loadStepData() }
        } else {
            stepStatus.value = StepDataStatus.PermissionRequired
            statusMessage.value = "StepWise needs Health Connect permission to read your steps."
        }
    }

    private suspend fun loadStepData() {
        stepStatus.value = StepDataStatus.Checking
        statusMessage.value = null
        try {
            todaySteps.value = healthConnect.readTodaySteps()
            weeklySteps.value = healthConnect.readWeeklySteps()
            stepStatus.value = StepDataStatus.Ready
        } catch (e: SecurityException) {
            stepStatus.value = StepDataStatus.PermissionRequired
            statusMessage.value = "StepWise needs Health Connect permission to read your steps."
        } catch (e: Exception) {
            stepStatus.value = StepDataStatus.Error
            statusMessage.value = e.localizedMessage ?: "Could not read step data."
        }
    }
}
