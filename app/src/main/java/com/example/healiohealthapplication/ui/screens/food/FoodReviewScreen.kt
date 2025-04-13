// File: com/example/healiohealthapplication/ui/screens/food/FoodReviewScreen.kt
package com.example.healiohealthapplication.ui.screens.food

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.healiohealthapplication.data.models.FoodProduct

@Composable
fun FoodReviewScreen(
    selectedFood: FoodProduct?
) {
    if (selectedFood == null) {
        Text("No food selected.")
        return
    }
    var servingSize by remember { mutableStateOf("100") } // default serving size in grams
    val size = servingSize.toFloatOrNull() ?: 100f

    // Calculate nutrient values adjusted for the serving size.
    val calories = (selectedFood.nutriments?.caloriesPer100g ?: 0f) * size / 100f
    val carbs = (selectedFood.nutriments?.carbsPer100g ?: 0f) * size / 100f
    val protein = (selectedFood.nutriments?.proteinPer100g ?: 0f) * size / 100f
    val fat = (selectedFood.nutriments?.fatPer100g ?: 0f) * size / 100f

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Product: ${selectedFood.productName ?: "Unknown"}")
        OutlinedTextField(
            value = servingSize,
            onValueChange = { servingSize = it.filter { ch -> ch.isDigit() } },
            label = { Text("Serving size (g)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("For $servingSize g:")
        Text("Calories: ${calories.toInt()}")
        Text("Carbs: ${carbs} g")
        Text("Protein: ${protein} g")
        Text("Fat: ${fat} g")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Daily Consumption Progress:", modifier = Modifier.padding(bottom = 8.dp))
        // Visualization using progress indicators
        NutrientProgress(label = "Calories", value = calories, goal = 2000f)
        NutrientProgress(label = "Carbs", value = carbs, goal = 300f)
        NutrientProgress(label = "Protein", value = protein, goal = 50f)
        NutrientProgress(label = "Fat", value = fat, goal = 70f)
    }
}

@Composable
fun NutrientProgress(label: String, value: Float, goal: Float) {
    val progress = (value / goal).coerceIn(0f, 1f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = "$label: ${value.toInt()} / ${goal.toInt()}", modifier = Modifier.fillMaxWidth())
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}
