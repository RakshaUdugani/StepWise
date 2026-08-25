package com.example.stepwise.permissions

import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord

object StepPermissions {
    val READ_STEPS: Set<String> = setOf(
        HealthPermission.getReadPermission(StepsRecord::class)
    )
}
