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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.ui.components.OverlappingCircle
import com.example.healiohealthapplication.ui.screens.shared.SharedViewModel
import com.example.healiohealthapplication.ui.theme.Green142
import androidx.compose.runtime.setValue
import android.content.Context
import androidx.work.*
import com.example.healiohealthapplication.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

@Composable
fun DietScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DietViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {

    val dietState = viewModel.diet.collectAsState().value

    val userData by sharedViewModel.userData.collectAsState()

    val userId = userData?.id

    // Load diet data on screen start
    LaunchedEffect(key1 = userId) {
        if (userId != null) {
            viewModel.loadDietData(userId)
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
                Text(
                    text = "myfitnesspal",  //TODO: Get the actual user name
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black)
                )

                // Right: Premium label + Notification icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Premium \uD83D\uDE0A",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Notification Icon button
                    IconButton(onClick = { /* TODO: Handle notification click */ }) {
                        /*Icon(
                            painter = painterResource(id = R.drawable.ic_notifications),
                            contentDescription = "Notifications",
                            tint = Color.Gray
                        )*/
                    }
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
    var reminderInterval by remember { mutableStateOf(2f) } // Default 2 hours
    val context = LocalContext.current
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                color = Green142,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Row for icon, text, and button
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Water bottle icon (placeholder)
            /*Image(
                painter = painterResource(id = R.drawable.ic_water_bottle), // Replace with your resource
                contentDescription = "Water bottle",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Fit
            )*/

            // Column with text and button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Drink enough water\nStay hydrated",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        scheduleOneTimeWaterReminder(context, 1000) // Delay 1 second for testing
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF404A48))
                ) {
                    Text(text = "Test Notification", color = Color.White)
                }

                // Button with glass icon
                /*Button(
                    onClick = { /* TODO: handle click */
                        scheduleWaterReminder(context, 100000)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF404A48))
                ) {
                    // Glass icon
                    /*Icon(
                        painter = painterResource(id = R.drawable.ic_glass), // Replace with your resource
                        contentDescription = "Glass icon",
                        tint = Color.White
                    )*/
                    /*Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Drink now", color = Color.White)

                    Text(
                        text = "Set water reminder (every ${reminderInterval.toInt()} hours)",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )

                    Slider(
                        value = reminderInterval,
                        onValueChange = { reminderInterval = it },
                        valueRange = 1f..6f,
                        steps = 4,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(onClick = {
                        scheduleWaterReminder(context, reminderInterval.toLong())
                    }) {
                        Text("Set Water Reminder")
                    }*/
                }*/
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

fun scheduleOneTimeWaterReminder(context: Context, delayMillis: Long) {
    val workRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
        .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "WaterReminderTest",
        ExistingWorkPolicy.REPLACE,
        workRequest
    )
}
