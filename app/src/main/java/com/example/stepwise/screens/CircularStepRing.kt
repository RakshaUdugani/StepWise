package com.example.stepwise.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularStepRing(
    steps: Int,
    goal: Int
) {
    val progress = if (goal > 0) steps.toFloat() / goal else 0f
    val animated = animateFloatAsState(targetValue = progress.coerceAtMost(1f))

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(220.dp)) {

        Canvas(modifier = Modifier.size(220.dp)) {
            drawCircle(
                color = Color(0xFFE1BEE7),
                style = Stroke(width = 18f)
            )

            drawArc(
                brush = Brush.linearGradient(
                    listOf(Color(0xFFBA68C8), Color(0xFF8E24AA))
                ),
                startAngle = -90f,
                sweepAngle = animated.value * 360f,
                useCenter = false,
                style = Stroke(width = 18f, cap = StrokeCap.Round),
                topLeft = Offset(0f, 0f),
                size = size
            )
        }

        Text(
            text = "${(animated.value * 100).toInt()}%",
            fontSize = 28.sp,
            color = Color(0xFF6A1B9A)
        )
    }
}
