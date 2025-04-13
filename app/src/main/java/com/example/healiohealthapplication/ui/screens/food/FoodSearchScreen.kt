// File: com/example/healiohealthapplication/ui/screens/food/FoodSearchScreen.kt
package com.example.healiohealthapplication.ui.screens.food

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healiohealthapplication.data.models.FoodProduct
import com.example.healiohealthapplication.ui.screens.food.FoodSearchViewModel

@Composable
fun FoodSearchScreen(
    onFoodSelected: (FoodProduct) -> Unit,
    foodSearchViewModel: FoodSearchViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    val foodResults by foodSearchViewModel.foodResults.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (query.length >= 3) {
                    foodSearchViewModel.searchFood(query)
                }
            },
            label = { Text("Search Food") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(foodResults) { food ->
                Text(
                    text = food.productName ?: "Unknown",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFoodSelected(food) }
                        .padding(8.dp)
                )
            }
        }
    }
}
