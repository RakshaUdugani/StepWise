package com.example.stepwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stepwise.ui.theme.StepWiseTheme

class PermissionsRationaleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepWiseTheme {
                Column(Modifier.padding(24.dp)) {
                    Text(
                        "StepWise reads your step count from Health Connect to show today's total and a weekly overview. Step data is read only."
                    )
                }
            }
        }
    }
}
