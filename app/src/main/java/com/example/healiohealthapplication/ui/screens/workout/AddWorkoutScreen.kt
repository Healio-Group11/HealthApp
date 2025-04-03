package com.example.healiohealthapplication.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import com.example.healiohealthapplication.data.models.Workout
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar

@Composable
fun AddWorkoutScreen(navController: NavController) {
    var workoutName by remember { mutableStateOf("") }
    var workoutDuration by remember { mutableStateOf("") }

    val darkTeal = Color(0xFF00796B)
    val lightTeal = Color(0xFF80CBC4)

    Scaffold(
        topBar = { TopNavBar("Add New Workout", navController) },
        bottomBar = { BottomNavBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Add New Workout", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

            // Input for workout name
            TextField(
                value = workoutName,
                onValueChange = { workoutName = it },
                label = { Text("Workout Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightTeal.copy(alpha = 0.2f), RoundedCornerShape(8.dp)), // Set background to light teal
                singleLine = true,
                // Manually change the indicator color without using textFieldColors
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = lightTeal,  // Focused indicator color as light teal
                    unfocusedIndicatorColor = Color.Gray // Unfocused indicator color as gray
                )
            )

            // Input for workout duration
            TextField(
                value = workoutDuration,
                onValueChange = { workoutDuration = it },
                label = { Text("Workout Duration (e.g., 30 min)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightTeal.copy(alpha = 0.2f), RoundedCornerShape(8.dp)), // Set background to light teal
                singleLine = true,
                // Manually change the indicator color without using textFieldColors
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = lightTeal,  // Focused indicator color as light teal
                    unfocusedIndicatorColor = Color.Gray // Unfocused indicator color as gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Save workout logic (navigate back for now)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = darkTeal)
            ) {
                Text("Save Workout", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}






