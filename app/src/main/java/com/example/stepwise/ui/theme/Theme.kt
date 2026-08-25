package com.example.stepwise.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PurpleMedium,
    onPrimary = Color.White,
    secondary = PurpleLight,
    tertiary = PurpleDark,
    background = PurpleLight,
    surface = Color.White,
    onBackground = TextPurple,
    onSurface = TextPurple
)

@Composable
fun StepWiseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
