package com.example.healiohealthapplication.ui.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.data.models.Workout
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WorkoutScreen(navController: NavController, userId: String, workoutViewModel: WorkoutViewModel = hiltViewModel()) {
    val workouts by workoutViewModel.workouts.collectAsState()
    val isLoading by workoutViewModel.isLoading.collectAsState()
    val errorMessage by workoutViewModel.errorMessage.collectAsState()

    // Fetch workouts when the screen is first loaded
    LaunchedEffect(userId) {
        workoutViewModel.fetchWorkouts(userId)
    }

    Scaffold(
        topBar = { TopNavBar("Workouts", navController) },
        bottomBar = { BottomNavBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Workout Tracker", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

            // Show loading indicator
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            // Show error message if any
            errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            // Display workouts
            workouts.forEach { workout ->
                WorkoutCard(
                    workout = workout,
                    onEdit = { navController.navigate("edit_workout/$userId/${workout.id}/${workout.name}/${workout.duration}") },
                    onDelete = {
                        workoutViewModel.deleteWorkout(userId, workout.id) { success ->
                            if (success) {
                                // Handle successful deletion
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("add_workout/$userId") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("Add New Workout", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Button(
                onClick = { navController.navigate("view_progress/$userId") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("View Progress", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}



@Composable
fun WorkoutCard(workout: Workout, onEdit: () -> Unit, onDelete: () -> Unit) {
    var isTimerRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0) }

    // Timer logic: Run timer when the timer is started and stop when paused
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (elapsedTime < workout.progress * 60) {  // Assuming progress is in minutes
                delay(1000)
                elapsedTime += 1
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(workout.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(workout.duration, fontSize = 14.sp, color = Color.Gray)
                }
                Row {
                    // Edit Button
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    // Delete Button
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Displaying progress using LinearProgressIndicator
            LinearProgressIndicator(
                progress = workout.progress / 100f,  // Assuming progress is a percentage (0-100)
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Timer functionality for workout progress tracking
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { isTimerRunning = !isTimerRunning }
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = if (isTimerRunning) "Pause Timer" else "Start Timer",
                    fontSize = 16.sp
                )
            }

            // Displaying elapsed time
            Text(
                text = "Elapsed Time: ${elapsedTime / 60}:${elapsedTime % 60}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}







