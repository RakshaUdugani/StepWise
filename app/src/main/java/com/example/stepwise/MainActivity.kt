package com.example.stepwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.stepwise.navigation.StepWiseApp
import com.example.stepwise.ui.theme.StepWiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StepWiseTheme {
                // No arguments – StepWiseApp creates its own NavHostController
                StepWiseApp()
            }
        }
    }
}
