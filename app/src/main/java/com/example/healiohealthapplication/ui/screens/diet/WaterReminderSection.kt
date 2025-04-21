package com.example.healiohealthapplication.ui.screens.diet

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.healiohealthapplication.ui.components.PermissionRequester
import com.example.healiohealthapplication.ui.theme.Green142
import com.example.healiohealthapplication.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

@Composable
fun WaterReminderSection(dietViewModel: DietViewModel) {
    val context = LocalContext.current

    // UI state for permissions
    val hasPermission by dietViewModel.hasPermission.collectAsState()

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
                    checked = reminderOn && hasPermission,
                    onCheckedChange = { isChecked ->
                        if (hasPermission) {
                            reminderOn = isChecked
                        } else {
                            Log.w(
                                "WaterReminder",
                                "Notifications not allowed — can't turn on reminder."
                            )
                        }
                    },
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