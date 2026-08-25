package com.example.stepwise.healthconnect

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.stepwise.permissions.StepPermissions
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

enum class HealthConnectAvailability {
    AVAILABLE,
    UNAVAILABLE,
    UPDATE_REQUIRED
}

class HealthConnectManager(private val context: Context) {

    fun availability(): HealthConnectAvailability {
        if (android.os.Build.VERSION.SDK_INT < 26) {
            return HealthConnectAvailability.UNAVAILABLE
        }
        return when (HealthConnectClient.getSdkStatus(context)) {
            HealthConnectClient.SDK_AVAILABLE -> HealthConnectAvailability.AVAILABLE
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED ->
                HealthConnectAvailability.UPDATE_REQUIRED
            else -> HealthConnectAvailability.UNAVAILABLE
        }
    }

    fun permissionContract() =
        PermissionController.createRequestPermissionResultContract()

    suspend fun hasReadStepsPermission(): Boolean {
        val client = clientOrNull() ?: return false
        val granted = client.permissionController.getGrantedPermissions()
        return StepPermissions.READ_STEPS.all { it in granted }
    }

    suspend fun readTodaySteps(): Int {
        val zone = ZoneId.systemDefault()
        val start = LocalDate.now(zone).atStartOfDay(zone).toInstant()
        return aggregateCount(start, Instant.now())
    }

    suspend fun readWeeklySteps(): List<Int> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        return (6 downTo 0).map { offset ->
            val date = today.minusDays(offset.toLong())
            val start = date.atStartOfDay(zone).toInstant()
            val end = if (date == today) {
                Instant.now()
            } else {
                date.plusDays(1).atStartOfDay(zone).toInstant()
            }
            aggregateCount(start, end)
        }
    }

    fun installOrUpdateIntent(): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "market://details?id=com.google.android.apps.healthdata"
            )
            setPackage("com.android.vending")
        }
    }

    private fun clientOrNull(): HealthConnectClient? {
        if (availability() != HealthConnectAvailability.AVAILABLE) return null
        return HealthConnectClient.getOrCreate(context)
    }

    private suspend fun aggregateCount(start: Instant, end: Instant): Int {
        val client = clientOrNull()
            ?: throw IllegalStateException("Health Connect is not available")
        if (end <= start) return 0
        val response = client.aggregate(
            AggregateRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(start, end)
            )
        )
        return (response[StepsRecord.COUNT_TOTAL] ?: 0L).toInt()
    }
}
