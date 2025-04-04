package com.example.healiohealthapplication.ui.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.models.Workout
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.ui.components.BottomNavBar

@Composable
fun EditWorkoutScreen(
    userId: String,
    workoutId: String,  // Changed from workoutName to workoutId
    workoutName: String,
    workoutDuration: String,
    navController: NavController,
    viewModel: WorkoutViewModel = hiltViewModel() // Inject ViewModel instead of repository
) {
    var updatedWorkoutName by remember { mutableStateOf(workoutName) }
    var updatedDuration by remember { mutableStateOf(workoutDuration) }
    var isSaving by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopNavBar(title = "Edit Workout", navController = navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Edit Workout", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            // TextField for workout name
            OutlinedTextField(
                value = updatedWorkoutName,
                onValueChange = { updatedWorkoutName = it },
                label = { Text("Workout Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // TextField for workout duration
            OutlinedTextField(
                value = updatedDuration,
                onValueChange = { updatedDuration = it },
                label = { Text("Workout Duration (minutes)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Save Button
            Button(
                onClick = {
                    isSaving = true
                    val updatedWorkout = Workout(updatedWorkoutName, updatedDuration, 50) // Assuming 50 is the progress
                    viewModel.updateWorkout(userId, workoutId, updatedWorkout) { success ->
                        isSaving = false
                        if (success) {
                            navController.popBackStack() // Go back to the previous screen on success
                        } else {
                            // Handle failure (could be a Toast or an error message)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Save Changes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Cancel Button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)) // Red for cancel
            ) {
                Text("Cancel", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
