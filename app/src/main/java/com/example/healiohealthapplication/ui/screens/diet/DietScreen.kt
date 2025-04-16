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
import android.util.Log
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.work.*
import com.example.healiohealthapplication.data.models.FoodProduct
import com.example.healiohealthapplication.ui.screens.food.FoodSearchViewModel
import com.example.healiohealthapplication.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit


@Composable
fun DietScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    dietViewModel: DietViewModel = hiltViewModel(),
    foodSearchViewModel: FoodSearchViewModel = hiltViewModel()
) {

    // Current macros from dietState
    val dietState = dietViewModel.diet.collectAsState().value
    val userData by sharedViewModel.userData.collectAsState()
    val userId = userData?.id
    val userName = userData?.email?.substringBefore("@")?.take(20)

    Log.d("DietScreen", "UserData in DietScreen: $userData")

    Log.d("userId", "User -> Id: ${userId}")


    // Local UI state for the search field and selected food
    var query by remember { mutableStateOf("") }
    var selectedFood by remember { mutableStateOf<FoodProduct?>(null) }
    val foodResults by foodSearchViewModel.foodResults.collectAsState()

    // Load diet data on screen start
    LaunchedEffect(userId) {
        if (userId != null) {
            dietViewModel.loadDietData(userId)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Overlapping circles at the top left
        OverlappingCircle()

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
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

                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // 2) "Today" Title
            Text(
                text = "Today",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3) Macros in separate rows (one per row)
            MacroRow(label = "Net Carbs", value = dietState.netCarbs.toString(), unit = "")
            Spacer(modifier = Modifier.height(16.dp))
            MacroRow(label = "Fat", value = dietState.fat.toString(), unit = "")
            Spacer(modifier = Modifier.height(16.dp))
            MacroRow(label = "Protein", value = dietState.protein.toString(), unit = "")
            Spacer(modifier = Modifier.height(16.dp))


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
                Spacer(modifier = Modifier.width(8.dp))
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
                Spacer(modifier = Modifier.height(8.dp))
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

            Spacer(modifier = Modifier.height(36.dp))

            // 4) Water Reminder Section
            WaterReminderSection()

            Spacer(modifier = Modifier.height(36.dp))

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


/**
 * Adds the selected food's nutritional values (assumed for 100g serving) to the current diet.
 */
private fun addFoodToDiet(
    food: FoodProduct,
    userId: String,
    dietViewModel: DietViewModel
) {
    // Retrieve the current diet
    val currentDiet = dietViewModel.diet.value

    // Calculate the added values (assumed for a 100g serving)
    val addedCarbs = food.nutriments?.carbsPer100g?.toInt() ?: 0
    val addedFat = food.nutriments?.fatPer100g?.toInt() ?: 0
    val addedProtein = food.nutriments?.proteinPer100g?.toInt() ?: 0

    // Debug prints before updating
    Log.d("DietUpdate", "Current Diet -> Net Carbs: ${currentDiet.netCarbs}, Fat: ${currentDiet.fat}, Protein: ${currentDiet.protein}")
    Log.d("DietUpdate", "Nutrients to add -> Carbs: $addedCarbs, Fat: $addedFat, Protein: $addedProtein")

    // Calculate updated diet values
    val updatedDiet = currentDiet.copy(
        netCarbs = currentDiet.netCarbs + addedCarbs,
        fat = currentDiet.fat + addedFat,
        protein = currentDiet.protein + addedProtein
    )

    // Debug print for new diet values
    Log.d("DietUpdate", "Updated Diet -> Net Carbs: ${updatedDiet.netCarbs}, Fat: ${updatedDiet.fat}, Protein: ${updatedDiet.protein}")

    // Update the diet using the ViewModel
    dietViewModel.updateDiet(userId, updatedDiet)
}

@Composable
fun MacroRow(label: String, value: String, unit: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF7F7F7),
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                width = 2.dp,
                color = Green142,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Text(
                text = "$value $unit",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )
        }
    }
}

@Composable
fun WaterReminderSection() {
    val context = LocalContext.current

    // Local states
    var reminderOn by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var selectedInterval by remember { mutableStateOf(2) } // default 2 hours
    val reminderOptions = listOf(1, 2, 3, 4, 5, 6)

    // Automatically schedule water reminder when reminder is on and interval changes.
    LaunchedEffect(reminderOn, selectedInterval) {
        if (reminderOn) {
            scheduleWaterReminder(context, selectedInterval.toLong())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)   // Set overall fixed height for consistency.
            .background(color = Color.White, shape = RoundedCornerShape(24.dp))
            .padding(0.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Row 1: Fixed reminder text box + Switch.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),   // Fixed row height.
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Fixed-size Box for reminder text.
                Box(
                    modifier = Modifier
                        .width(280.dp)   // fixed width
                        .height(60.dp)   // fixed height matching row
                        .border(width = 2.dp, color = Green142, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = if (reminderOn) "Water Reminder: On (${selectedInterval}h)" else "Water Reminder: Off",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                        maxLines = 1
                    )
                }
                // Switch, scaled up, with Green142 colors.
                Switch(
                    checked = reminderOn,
                    onCheckedChange = { reminderOn = it },
                    modifier = Modifier.scale(1.3f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Green142,
                        checkedTrackColor = Green142.copy(alpha = 0.5f),
                        uncheckedThumbColor = Green142,
                        uncheckedTrackColor = Green142.copy(alpha = 0.5f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Row 2: Dropdown button for selecting the reminder interval.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),  // Fixed row height.
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .height(50.dp)
                            .widthIn(min = 200.dp),  // ensure a minimum width
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Green142,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Every $selectedInterval h",
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        reminderOptions.forEach { hour ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedInterval = hour
                                    expanded = false
                                },
                                text = { Text("$hour h") }
                            )
                        }
                    }
                }
            }
        }
    }
}


fun scheduleWaterReminder(context: Context, intervalHours: Long) {
    val workRequest = PeriodicWorkRequestBuilder<WaterReminderWorker>(intervalHours, TimeUnit.HOURS)
        .setInitialDelay(intervalHours, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "WaterReminder",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
}

/*fun scheduleOneTimeWaterReminder(context: Context, delayMillis: Long) {
    val workRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
        .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "WaterReminderTest",
        ExistingWorkPolicy.REPLACE,
        workRequest
    )
}*/
