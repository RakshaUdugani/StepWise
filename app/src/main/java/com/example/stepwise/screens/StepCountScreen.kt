package com.example.stepwise.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stepwise.permissions.StepPermissions
import com.example.stepwise.viewmodels.StepCounterViewModel
import com.example.stepwise.viewmodels.StepDataStatus

@Composable
fun StepCountScreen(
    activity: Activity,
    viewModel: StepCounterViewModel = viewModel()
) {
    val gradient = Brush.verticalGradient(
        listOf(Color(0xFFF3E5F5), Color(0xFFE8D9F6))
    )

    val today by viewModel.todaySteps.observeAsState(0)
    val weekly by viewModel.weeklySteps.observeAsState(List(7) { 0 })
    val goal by viewModel.dailyGoal.collectAsState()
    val stepStatus by viewModel.stepStatus.observeAsState(StepDataStatus.Checking)
    val statusMessage by viewModel.statusMessage.observeAsState(null)

    var showGoalDialog by remember { mutableStateOf(false) }
    var requestedPermission by remember { mutableStateOf(false) }

    val permissionContract = remember { viewModel.permissionContract() }
    val permissionLauncher = rememberLauncherForActivityResult(
        permissionContract
    ) { granted ->
        viewModel.onPermissionResult(granted)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.checkAndLoad()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(stepStatus) {
        if (stepStatus == StepDataStatus.PermissionRequired && !requestedPermission) {
            requestedPermission = true
            permissionLauncher.launch(StepPermissions.READ_STEPS)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ⚙️ SETTINGS ICON
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { showGoalDialog = true }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF6A1B9A)
                )
            }
        }

        if (stepStatus != StepDataStatus.Ready && stepStatus != StepDataStatus.Checking) {
            Text(
                text = statusMessage ?: "",
                color = Color(0xFF6A1B9A),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            when (stepStatus) {
                StepDataStatus.PermissionRequired -> {
                    TextButton(onClick = {
                        requestedPermission = true
                        permissionLauncher.launch(StepPermissions.READ_STEPS)
                    }) {
                        Text("Allow step access", color = Color(0xFF6A1B9A))
                    }
                }
                StepDataStatus.UpdateRequired -> {
                    TextButton(onClick = {
                        activity.startActivity(viewModel.installOrUpdateIntent())
                    }) {
                        Text("Install Health Connect", color = Color(0xFF6A1B9A))
                    }
                }
                StepDataStatus.Error -> {
                    TextButton(onClick = { viewModel.checkAndLoad() }) {
                        Text("Retry", color = Color(0xFF6A1B9A))
                    }
                }
                else -> {}
            }
            Spacer(Modifier.height(16.dp))
        }

        // --- Today title ---
        Text(
            text = "Today's Steps",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF6A1B9A)
        )

        Spacer(Modifier.height(16.dp))

        // --- Today steps number ---
        Text(
            text = "$today",
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8E24AA)
        )

        Spacer(Modifier.height(40.dp))

        // --- Weekly graph title ---
        Text(
            text = "Weekly Overview",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF4A148C)
        )

        Spacer(Modifier.height(22.dp))

        WeeklyStepsGraph(weekly)

        Spacer(Modifier.height(40.dp))

        // ⭐ RING BELOW WEEKLY GRAPH
        if (goal > 0) {
            CircularStepRing(steps = today, goal = goal)
            Spacer(Modifier.height(20.dp))
        }
    }

    // ---- GOAL SETTINGS DIALOG ----
    if (showGoalDialog) {
        SetGoalDialog(
            currentGoal = goal,
            onSave = {
                viewModel.saveDailyGoal(it)
                showGoalDialog = false
            },
            onCancel = { showGoalDialog = false },
            onDelete = {
                viewModel.clearGoal()
                showGoalDialog = false
            }
        )
    }
}


@Composable
fun SetGoalDialog(
    currentGoal: Int,
    onSave: (Int) -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    var input by remember { mutableStateOf(if (currentGoal > 0) currentGoal.toString() else "") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Daily Step Goal") },
        text = {
            Column {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it.filter { c -> c.isDigit() } },
                    label = { Text("Step Goal") }
                )

                if (currentGoal > 0) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Current goal: $currentGoal steps",
                        color = Color(0xFF6A1B9A),
                        fontSize = 14.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(input.toIntOrNull() ?: 0)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                if (currentGoal > 0) {
                    TextButton(onClick = onDelete) {
                        Text("Delete Goal", color = Color.Red)
                    }
                }
                TextButton(onClick = onCancel) {
                    Text("Cancel")
                }
            }
        }
    )
}


@Composable
fun WeeklyStepsGraph(steps: List<Int>) {

    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val safe = if (steps.size < 7) steps + List(7 - steps.size) { 0 } else steps
    val max = safe.maxOrNull()?.takeIf { it > 0 } ?: 1

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        safe.forEachIndexed { index, count ->
            val height = ((count.toFloat() / max) * 160f).coerceAtLeast(40f)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = count.toString(), fontSize = 12.sp, color = Color(0xFF6A1B9A))
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .height(height.dp)
                        .width(22.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFE1BEE7), Color(0xFFBA68C8))
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                )
                Spacer(Modifier.height(6.dp))
                Text(text = days[index], fontSize = 12.sp, color = Color(0xFF4A148C))
            }
        }
    }
}
