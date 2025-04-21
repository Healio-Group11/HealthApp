package com.example.healiohealthapplication.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.healiohealthapplication.R
import com.example.healiohealthapplication.utils.Permissions

class WaterReminderWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val permissions: Permissions
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Check for POST_NOTIFICATIONS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = permissions.hasNotificationPermission()
            if (!hasPermission) {
                return Result.failure()
            }
        }
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val channelId = "water_reminder_channel"
        val notificationId = 101

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Water Reminder"
            val descriptionText = "Reminds you to drink water"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_person_drinking_water)  // make sure this drawable exists
            .setContentTitle("Hydration Time 💧")
            .setContentText("Time to drink some water!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)
    }
}
