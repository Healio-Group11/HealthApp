package com.example.healiohealthapplication.ui.screens.calendar

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CalendarScreen(navController: NavController, modifier: Modifier) {
    Scaffold(
        topBar = { TopNavBar("Calendar", navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Calendar Component in a Box
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            ) {
                CalendarView()
            }

            // Current Date Text
            CurrentDateDisplay()

            // Reminder Section with Scroll
            ReminderList()
        }
    }
}

@Composable
fun CalendarView() {
    val calendar = Calendar.getInstance()

    // Get the current month and year
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)

    // Get the first day of the month and how many days in the month
    calendar.set(currentYear, currentMonth, 1)
    val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
    val numberOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    // Adjust first day of the month for correct alignment (Sunday as 0)
    val firstDayAdjusted = if (firstDayOfMonth == 1) 7 else firstDayOfMonth - 1

    // List of days in the month (with padding at the start for the first day)
    val days = List(numberOfDaysInMonth + firstDayAdjusted - 1) { index ->
        if (index < firstDayAdjusted - 1) null else index - firstDayAdjusted + 2
    }

    // Current day of the month
    val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    // Get the name of the current month
    val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.time)

    // Weekday labels
    val weekdays = listOf("S", "M", "T", "W", "T", "F", "S") // Starting from Sunday

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(2.dp, Color.Gray, shape = MaterialTheme.shapes.medium) // Border around the calendar
            .padding(16.dp) // Padding inside the box
    ) {
        // Display the current month at the top
        Text(
            text = monthName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // Weekday labels
        Row(modifier = Modifier.fillMaxWidth()) {
            weekdays.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7), // 7 columns for the days of the week (Sun-Sat)
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 250.dp) // Ensure the calendar has enough height
        ) {
            items(days.size) { index ->
                val day = days[index]
                if (day != null) {
                    DayBox(day = day, isToday = day == currentDay)
                } else {
                    Spacer(modifier = Modifier.height(40.dp)) // Placeholder for empty spaces
                }
            }
        }
    }
}

@Composable
fun DayBox(day: Int, isToday: Boolean) {
    val backgroundColor = if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isToday) Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp) // Set a fixed size for each day box
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}



@Composable
fun CurrentDateDisplay() {
    val currentDate = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(Date())
    Text(
        text = currentDate,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ReminderList() {
    // Sample reminders for the day
    val reminders = listOf(
        "Nothing planned. Tap to create.",
        "Water Intake: 2L",
        "Medicine Reminder: Napa",
        "Workout Reminder: Plank",
        "Steps Reminder: 10,000 steps",
        "Additional Reminder 3",
        "Additional Reminder 4",
        "Additional Reminder 5",
        "Additional Reminder 6" // More reminders added
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Reminders",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Add vertical scrolling
        ) {
            reminders.forEachIndexed { index, reminder ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF80CBC4), shape = MaterialTheme.shapes.small)  // Use rounded corners here
                        .padding(16.dp)
                        .padding(vertical = 8.dp)  // Adds vertical padding to each reminder
                ) {
                    Text(
                        text = reminder,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                // Add a Spacer to create vertical space between the boxes
                if (index != reminders.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))  // create the space between the boxes
                }
            }
        }
    }
}