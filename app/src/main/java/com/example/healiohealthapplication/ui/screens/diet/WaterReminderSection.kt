package com.example.healiohealthapplication.ui.screens.diet

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import android.Manifest
import com.example.healiohealthapplication.ui.theme.Green142
import com.example.healiohealthapplication.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit
import androidx.activity.compose.rememberLauncherForActivityResult

@Composable
fun WaterReminderSection() {
    val context = LocalContext.current

    // Local states
    var reminderOn by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var selectedInterval by remember { mutableStateOf(2) } // default 2 hours
    val reminderOptions = listOf(1, 2, 3, 4, 5, 6)

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Schedule only if granted
                scheduleWaterReminder(context, selectedInterval.toLong())
            } else {
                Toast.makeText(context, "Notification permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Automatically schedule water reminder when reminder is on and interval changes
    LaunchedEffect(reminderOn, selectedInterval) {
        if (reminderOn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = Manifest.permission.POST_NOTIFICATIONS
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionLauncher.launch(permission)
                } else {
                    scheduleWaterReminder(context, selectedInterval.toLong())
                }
            } else {
                // No permission needed below Android 13
                scheduleWaterReminder(context, selectedInterval.toLong())
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(color = Color.White, shape = RoundedCornerShape(24.dp))
            .padding(0.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Row 1: Text + Switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(60.dp)
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

            // Row 2: Interval selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .height(50.dp)
                            .widthIn(min = 200.dp),
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