package com.example.healiohealthapplication.ui.screens.diet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.ui.components.OverlappingCircle
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.theme.Green142
import androidx.compose.runtime.setValue
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.work.*
import com.example.healiohealthapplication.data.models.Diet
import com.example.healiohealthapplication.data.models.FoodProduct
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.screens.food.FoodSearchViewModel
import com.example.healiohealthapplication.worker.WaterReminderWorker
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import com.example.healiohealthapplication.ui.screens.diet.addFoodToDiet


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DietScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    dietViewModel: DietViewModel = hiltViewModel(),
    foodSearchViewModel: FoodSearchViewModel = hiltViewModel()
) {

    // Current macros from dietState
    val userData by sharedViewModel.userData.collectAsState()
    val userId = userData?.id
    val userName = userData?.email?.substringBefore("@")?.take(20)
    val spacerDp = 12.dp

    Log.d("DietScreen", "UserData in DietScreen: $userData")

    Log.d("userId", "User -> Id: ${userId}")


    // Local UI state for the search field and selected food
    var query by remember { mutableStateOf("") }
    var selectedFood by remember { mutableStateOf<FoodProduct?>(null) }
    val foodResults by foodSearchViewModel.foodResults.collectAsState()

    val today = LocalDate.now().toString()
    LaunchedEffect(userId) {
        if (userId != null) {
            dietViewModel.loadDietForDate(userId, today)
        }
    }

    val dietForToday by dietViewModel.currentDiet.collectAsState()

    Scaffold(
        topBar = {
            TopNavBar(
                title = stringResource(R.string.diet_screen_title),
                navController = navController
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        // optional: floatingActionButton, etc.
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OverlappingCircle()
        }

        // Main content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: User name
                    if (userName != null) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
                        )
                    }

                    // Right: Premium label + Notification icon
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Premium \uD83D\uDE0A",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                        )

                        Spacer(modifier = Modifier.width(spacerDp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(spacerDp))

                // 2) "Today" Title
                Text(
                    text = "Today",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(spacerDp))

                // 3) Macros in separate rows (one per row)
                MacroRow(label = "Net Carbs", value = dietForToday?.netCarbs.toString(), unit = "")
                Spacer(modifier = Modifier.height(spacerDp))
                MacroRow(label = "Fat", value = dietForToday?.fat.toString(), unit = "")
                Spacer(modifier = Modifier.height(spacerDp))
                MacroRow(label = "Protein", value = dietForToday?.protein.toString(), unit = "")
                Spacer(modifier = Modifier.height(spacerDp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = if (selectedFood != null) selectedFood!!.productName ?: query else query,
                        onValueChange = { newValue ->
                            query = newValue
                            // Clear previous selection if text changes
                            selectedFood = null
                            if (query.length >= 3) {
                                foodSearchViewModel.searchFood(query)
                            }
                        },
                        label = { Text("Consumed Food") },
                        textStyle = TextStyle(color = Color.Black),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Green142,
                            unfocusedBorderColor = Green142,
                            cursorColor = Green142
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    )
                    Spacer(modifier = Modifier.width(spacerDp))
                    Button(
                        onClick = {
                            if (selectedFood != null && userId != null) {
                                addFoodToDiet(selectedFood!!, userId, dietViewModel)
                                query = ""
                                selectedFood = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green142),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Consumed Food",
                            tint = Color.White
                        )
                    }
                }

                // --- End of Input Row ---

                // Show suggestions if query is not empty and no selection has been made
                if (query.length >= 3 && selectedFood == null) {
                    Spacer(modifier = Modifier.height(spacerDp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 300.dp)
                    ) {
                        items(items = foodResults) { food ->
                            Text(
                                text = food.productName ?: "Unknown",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // Set the selected food and update text field accordingly
                                        selectedFood = food
                                        query = food.productName ?: ""
                                    }
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(spacerDp))
            }

            item {
                // 4) Water Reminder Section
                WaterReminderSection()

                Spacer(modifier = Modifier.height(spacerDp))

                Image(
                    painter = painterResource(id = R.drawable.ic_person_drinking_water),
                    contentDescription = "Person drinking water",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

/**
 * Adds the selected food's nutritional values (assumed for 100g serving)
 * to the diet entry for the current date.
 */
private fun addFoodToDiet(
    food: FoodProduct,
    userId: String,
    dietViewModel: DietViewModel
) {
    // Determine today’s date key (must match how the ViewModel was called)
    val today = LocalDate.now().toString()

    // Retrieve the current diet for today, or initialize a new one
    val currentDiet = dietViewModel.currentDiet.value ?: Diet(id = today)

    // Calculate added macros (per 100g)
    val addedCarbs   = food.nutriments?.carbsPer100g?.toInt()   ?: 0
    val addedFat     = food.nutriments?.fatPer100g?.toInt()     ?: 0
    val addedProtein = food.nutriments?.proteinPer100g?.toInt() ?: 0

    // Debug before update
    Log.d("DietUpdate", "[$today] Before → carbs=${currentDiet.netCarbs}, fat=${currentDiet.fat}, protein=${currentDiet.protein}")

    // Build the updated Diet object
    val updatedDiet = currentDiet.copy(
        netCarbs   = currentDiet.netCarbs   + addedCarbs,
        fat        = currentDiet.fat        + addedFat,
        protein    = currentDiet.protein    + addedProtein
    )

    // Debug after update
    Log.d("DietUpdate", "[$today] After  → carbs=${updatedDiet.netCarbs}, fat=${updatedDiet.fat}, protein=${updatedDiet.protein}")

    // Persist via ViewModel (which will also update the currentDiet StateFlow)
    dietViewModel.saveDietForDate(userId = userId, date = today, updated = updatedDiet)
}
