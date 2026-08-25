package com.example.stepwise.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.stepwise.data.UserPreferences

class BMIViewModel(context: Context) : ViewModel() {
    private val prefs = UserPreferences(context.applicationContext)
    val savedBMI = prefs.bmi
    fun saveBMI(value: Float) = prefs.saveBMI(value)
}

fun interpretBMI(bmi: Float): String = when {
    bmi < 18.5f -> "Underweight"
    bmi < 25f -> "Normal weight"
    bmi < 30f -> "Pre-obesity"
    bmi < 35f -> "Obesity class I"
    bmi < 40f -> "Obesity class II"
    else -> "Obesity class III"
}

@Composable
fun BMICalculatorScreen(viewModel: BMIViewModel) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var attemptedCalculation by remember { mutableStateOf(false) }
    val savedBMI by viewModel.savedBMI.collectAsState()
    val heightValue = height.toFloatOrNull()
    val weightValue = weight.toFloatOrNull()
    val heightError = attemptedCalculation && (heightValue == null || heightValue !in 50f..300f)
    val weightError = attemptedCalculation && (weightValue == null || weightValue !in 10f..500f)

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFF1EB)).padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("BMI Calculator", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF4A148C))
        Text("Enter your measurements to calculate your body mass index.", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = height, onValueChange = { height = it.filter { c -> c.isDigit() || c == '.' } }, label = { Text("Height (cm)") }, isError = heightError, supportingText = { if (heightError) Text("Enter a height between 50 and 300 cm") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = weight, onValueChange = { weight = it.filter { c -> c.isDigit() || c == '.' } }, label = { Text("Weight (kg)") }, isError = weightError, supportingText = { if (weightError) Text("Enter a weight between 10 and 500 kg") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
        Button(onClick = {
            attemptedCalculation = true
            val validHeight = heightValue
            val validWeight = weightValue
            if (validHeight != null && validWeight != null && validHeight in 50f..300f && validWeight in 10f..500f) {
                viewModel.saveBMI(validWeight / ((validHeight / 100) * (validHeight / 100)))
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("Calculate BMI") }
        if (savedBMI > 0f) {
            Spacer(Modifier.height(8.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Your saved BMI", style = MaterialTheme.typography.labelLarge)
                    Text(String.format("%.1f", savedBMI), style = MaterialTheme.typography.displaySmall, color = Color(0xFF6A1B9A))
                    Text(interpretBMI(savedBMI), style = MaterialTheme.typography.titleMedium)
                    Text("BMI is a screening measure and does not replace advice from a healthcare professional.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
